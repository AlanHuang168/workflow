package org.apache.alan.wrokflow.interceptor;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.alan.wrokflow.annotation.WorkFlow;
import org.apache.alan.wrokflow.dto.Authentication;
import org.apache.alan.wrokflow.service.FlowInstanceService;
import org.apache.alan.wrokflow.utils.RequestHolder;
import org.apache.alan.wrokflow.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-07 17:11
 */
@Aspect
@Service("WorkFlowAspect")
public class WorkFlowAspect {

    @Autowired
    private FlowInstanceService flowInstanceService;

    @Pointcut("@annotation(org.apache.alan.wrokflow.annotation.WorkFlow)")
    public void serviceFlowAspect(){
    }

    @Before("serviceFlowAspect()")
    public void before(JoinPoint joinPoint) throws Exception {
    }

    @AfterReturning(value = "serviceFlowAspect()",returning = "methodResult")
    public void afterMethod(JoinPoint joinPoint, Object methodResult) throws Exception {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        WorkFlow workFlow = method.getAnnotation(WorkFlow.class);
        if(workFlow != null){
            Integer bId = 0;
            if(StringUtils.isNotEmpty(workFlow.moduleValue())){
                //参数未指定业务id
                if(StringUtils.isNotEmpty(workFlow.spelValue())){
                    String idValue = getBizId(workFlow.spelValue(), joinPoint);
                    bId = Integer.parseInt(idValue);
                    Authentication auth = RequestHolder.getAuthenticationInfo();
                    flowInstanceService.startEngine(workFlow.moduleValue(),bId,auth);
                }else{
                    //返回值指定业务id
                    String retJson = JSON.toJSONString(methodResult);
                    Map<String,Object> retMap = toMap(retJson);
                    if(retMap.size() > 0) {
                        String idValue = retMap.get("bId").toString();
                        bId = Integer.parseInt(idValue);
                        Authentication auth = RequestHolder.getAuthenticationInfo();
                        flowInstanceService.startEngine(workFlow.moduleValue(),bId,auth);
                    }
                }
            }

        }

    }

    @AfterThrowing("serviceFlowAspect()")
    public void afterThrowing()throws Throwable{
    }

    @After("serviceFlowAspect()")
    public void finalMethod(JoinPoint point){

    }


    @Around("serviceFlowAspect()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint)throws Throwable{
        return  joinPoint.proceed();
    }

    /**
     * @Description  获取注解中对方法的描述信息 用于service层注解
     */
    public static String getServiceMethodValue(JoinPoint joinPoint)throws Exception{
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String moduleValue = "";
        for (Method method:methods) {
            if (method.getName().equals(methodName)){
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length==arguments.length){
                    moduleValue = method.getAnnotation(WorkFlow.class).moduleValue();
                    break;
                }
            }
        }
        return moduleValue;
    }

    /**
     * 用于获取方法参数定义名字.  能根据你传入的方法 获取该方法的参数名
     */
    private static DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();
    /**
     * 用于SpEL表达式解析.
     */
    private static SpelExpressionParser parser = new SpelExpressionParser();

    public static String getBizId(String spELString, JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String[] paramNames = nameDiscoverer.getParameterNames(method);
        Expression expression = parser.parseExpression(spELString);
        EvaluationContext context = new StandardEvaluationContext();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        return expression.getValue(context).toString();
    }

    public static Map<String, Object> toMap(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String,Object>>() {
            });
        } catch (Exception e) {
            return null;
        }
    }
}
