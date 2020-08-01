package com.log;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class LogUtils {

    public static final String DEBUG_LOG = "DEBUG_LOG";

    /**
     * 开始打印日志
     * @param tag
     * @return
     */
    public static LogStack startLogWeb(String tag) {
        try {
            LogStack root = getRootLog();
            if(root == null) {
                return null;
            }
            return startLog(root, tag);
        } catch (Exception e) {
            //IGNORE 不能影响主流程
            return null;
        }
    }

    /**
     * 添加日志
     * @param text
     * @return
     */
    public static LogStack appendLogWeb(String text) {
        try {
            LogStack root = getRootLog();
            if(root == null) {
                return null;
            }
            return appendLog(root, text);
        } catch (Exception e) {
            //IGNORE 不能影响主流程
            return null;
        }
    }

    public static LogStack appendLogWeb(String text, Throwable throwable) {
        try {
            LogStack root = getRootLog();
            if(root == null) {
                return null;
            }
            return appendLog(root, text, throwable);
        } catch (Exception e) {
            //IGNORE 不能影响主流程
            return null;
        }
    }

    public static LogStack appendLogWeb(Object obj) {
        try {
            LogStack root = getRootLog();
            if(root == null) {
                return null;
            }
            return appendLog(root, ToStringBuilder.reflectionToString(obj,
            		 ToStringStyle.SHORT_PREFIX_STYLE));
        } catch (Exception e) {
            //IGNORE 不能影响主流程
            return null;
        }
    }
    
    /**
     * 结束打印本次日志
     * @return
     */
    public static LogStack endLogWeb() {
        try {
            LogStack root = getRootLog();
            if(root == null) {
                return null;
            }
            return endLog(root);
        } catch (Exception e) {
            //IGNORE 不能影响主流程
            return null;
        }
    }
    
    /**
     * 打印日志并结束
     * @param text 日志文本
     * @return
     * @author ltwangwei
     * @date 2016-1-21 下午1:38:35
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public static LogStack endLogWeb(String text){
    	appendLogWeb(text);
    	return endLogWeb();
    }

    /**
     * 合并打印日志
     * 目前只支持info级别，后期扩展
     * @param logger log4j
     * @param text 日志文本
     * @author ltwangwei
     * @date 2016-1-21 下午1:47:27
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public static void appendLogPrintWeb(Logger logger, String text){
    	if(logger != null && StringUtils.isNotBlank(text)){
    		logger.info(text);
    		appendLogWeb(text);
    	}
    }


    public static LogStack getRootLog() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return (LogStack) request.getAttribute(DEBUG_LOG);
    }

    public static LogStack initLog(String tag) {
        LogStack logStack = LogStack.create(tag);
        List<LogStack> subLogs = new ArrayList<LogStack>();
        logStack.setSubLogs(subLogs);
        return logStack;
    }

    public static LogStack startLog(LogStack root, String tag) {
        LogStack pendingLog = getPendingLog(root);
        if(pendingLog == null) return null;

        LogStack logStack = LogStack.create(tag);
        logStack.setDeep(pendingLog.getDeep() + 1);
        List<LogStack> subLogs;
        if(pendingLog.getSubLogs() == null) {
            subLogs = new ArrayList<LogStack>();
            pendingLog.setSubLogs(subLogs);
        } else {
            subLogs = pendingLog.getSubLogs();
        }
        subLogs.add(logStack);
        pendingLog.getSeq().add(logStack);
        return logStack;
    }

    public static LogStack appendLog(LogStack root, String text) {
        LogStack pendingLog = getPendingLog(root);
        if(pendingLog == null) return null;

        List<String> texts = pendingLog.getTexts();
        texts.add(text);
        pendingLog.getSeq().add(text);
        return pendingLog;
    }

    public static LogStack appendLog(LogStack root, String text, Throwable throwable) {
        LogStack pendingLog = getPendingLog(root);
        if(pendingLog == null) return null;

        String stackTrace = ExceptionUtils.getStackTrace(throwable);
        List<String> texts = pendingLog.getTexts();
        texts.add(text + "=> " + stackTrace);
        pendingLog.getSeq().add(text + "=> " + stackTrace);
        return pendingLog;
    }

    public static LogStack endLog(LogStack root) {
        LogStack pendingLog = getPendingLog(root);
        if(pendingLog == null) return null;

        pendingLog.end();
        return pendingLog;
    }

    /**
     * 结束整个log日志堆栈
     * @param log
     * @return
     */
    public static void endWholeLog(LogStack log) {
        if(log == null || log.getType() == LogStack.LogStackType.END) {
            return;
        }

        if(log.getType() == LogStack.LogStackType.PROCEED) {
            log.end();

            if (!CollectionUtils.isEmpty(log.getSubLogs())) {
                for (LogStack subLog : log.getSubLogs()) {
                    endWholeLog(subLog);
                }
            }
        }
    }

    private static LogStack getPendingLog(LogStack log) {
        //空或者结束,直接未找到
        if(log == null || log.getType() == LogStack.LogStackType.END) {
            return null;
        }
        //进行且没有孩子直接返回本身
        if(log.getType() == LogStack.LogStackType.PROCEED && CollectionUtils.isEmpty(log.getSubLogs())) {
            return log;
        }

        //递归遍历孩子
        for (LogStack subLog : log.getSubLogs()) {
            if(subLog.getType() == LogStack.LogStackType.PROCEED) {
                if(CollectionUtils.isEmpty(subLog.getSubLogs())) {
                    return subLog;
                }else{
                    return getPendingLog(subLog);
                }
            }
        }
        //孩子都已结束,直接返回本身
        return log;
    }


    public static void main(String[] args) {
        LogStack root = LogUtils.initLog("TEST");

        try {
            Thread.sleep(200);
        }catch (Exception e){
        }

        test1(root);
        test2(root);

        endWholeLog(root);

        System.out.println(new Gson().toJson(root));
        System.out.println(root);
    }

    private static void test1(LogStack root) {
        LogUtils.startLog(root, "A");

        test11(root);
        LogUtils.appendLog(root, "1 测试");
        try {
            test12(root);
            throw new Exception("华丽的异常");
        } catch (Exception e) {
            LogUtils.appendLog(root, "测试异常", e);
        }

        LogUtils.endLog(root);
    }

    private static void test11(LogStack root) {
        LogUtils.startLog(root, "A-1");
        try {
            Thread.sleep(200);
        }catch (Exception e){
        }
        LogUtils.appendLog(root, "1.1 测试");

        LogUtils.endLog(root);
    }

    private static void test12(LogStack root) {
        LogUtils.startLog(root, "A-2");
        try {
            Thread.sleep(200);
        }catch (Exception e){
        }
        LogUtils.appendLog(root, "1.2 测试");

        if(true) {
            throw new RuntimeException("TEST EXCEPTION!!!");
        }
        LogUtils.endLog(root);
    }

    private static void test2(LogStack root) {
        LogUtils.startLog(root, "B");
        try {
            Thread.sleep(200);
        }catch (Exception e){
        }
        LogUtils.appendLog(root, "2 测试");

        LogUtils.endLog(root);
    }


    /**
     * 合并打印日志
     * 目前只支持info级别，后期扩展
     * @param logger log4j
     * @param loggerLevel 日志级别(可扩展)
     * @param text 日志文本
     * @author ltwangwei
     * @date 2016-1-21 下午1:47:27
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public static void printLog4jAndWeb(Logger logger, String loggerLevel, String text){
        if(logger != null && StringUtils.isNotBlank(text)){
            logger.info(text);
            appendLogWeb(text);
        }
    }
}
