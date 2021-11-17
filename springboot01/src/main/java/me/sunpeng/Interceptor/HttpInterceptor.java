package me.sunpeng.Interceptor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.sunpeng.utils.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author sp
 * @date 2021-11-16 17:11
 */
@Slf4j
@Component
public class HttpInterceptor extends HandlerInterceptorAdapter {
    private ThreadLocal<Long> startTime = new ThreadLocal<>();
    /**
     * 预处理回调方法，实现处理器的预处理（如检查登陆），第三个参数为响应的处理器，自定义Controller
     * <p>
     * 返回值：
     * true表示继续流程（如调用下一个拦截器或处理器）
     * false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器
     * 此时我们需要通过response来产生响应；
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        startTime.set(System.currentTimeMillis());
        String uri = request.getRequestURI();
        Map paramMap = request.getParameterMap();
        log.info("用户访问地址:{},来路地址:{},请求参数:{}",uri, StringUtils.getIp(request),JSON.toJSON(paramMap));
        log.info("----------请求头.start......");
        final Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            log.info(name + ":{}",request.getHeader(name));
        }
        log.info("---------请求头.end......");
        return super.preHandle(request, response, handler);

    }

    /**
     * 在任何情况下都会对返回的请求做处理
     * <p>
     * 即在视图渲染完毕时回调，如性能监控中我们可以在此记录结束时间并输出消耗时间
     * 还可以进行一些资源清理，类似于try-catch-finally中的finally，但仅调用处理器执行链中
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("请求处理结束. 处理耗时: {}", System.currentTimeMillis() - startTime.get());
        startTime.remove();
        super.afterCompletion(request, response, handler, ex);
    }


}
