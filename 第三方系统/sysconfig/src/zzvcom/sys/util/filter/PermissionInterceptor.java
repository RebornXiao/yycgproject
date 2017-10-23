package zzvcom.sys.util.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import zzvcom.sys.action.First;
import zzvcom.sys.action.LoginAction;
import zzvcom.sys.pojo.VcomSysUser;
import zzvcom.sys.service.ModuleService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * 权限控制拦截器
 *
 */


public class PermissionInterceptor  extends AbstractInterceptor{
	
	private static final long serialVersionUID = -8148587933080816589L;
	private static final String NOSESSION = "sessionNotValid";
	private static final String LOGOIN = "loginshow";
	
	public String intercept(ActionInvocation invocation) throws Exception {
		Action action = (Action) invocation.getAction();
		HttpServletRequest request = ServletActionContext.getRequest();
		VcomSysUser usr=(VcomSysUser)request.getSession().getAttribute("userInfo");
		
		if(usr==null){
			if(action instanceof First){
				return LOGOIN;
			}
			if(!(action instanceof LoginAction)){
				return NOSESSION;
			}			
		}
		String mid = request.getParameter("mid");
        Map urlmap=(Map)request.getSession().getAttribute("listOperatemap");
        if(mid!=null){
        	if(urlmap==null)urlmap=new HashMap();
        	String url=request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf(".action"));
        	if(urlmap.get(url)==null){
        		urlmap.put(url, mid);
        		request.getSession().setAttribute("listOperatemap", urlmap);
        	}
        }else{
        	String namespace =ServletActionContext.getActionMapping().getNamespace();
        	String url=request.getContextPath()+(namespace==null?"":namespace)+"/"+invocation.getInvocationContext().getName();
            if(urlmap!=null&&urlmap.get(url)!=null){
            	mid=(String) urlmap.get(url);
            }
        }
        if(mid!=null){
        	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
            ModuleService modelService = (ModuleService) wac.getBean("moduleService");
            List operlist=modelService.getOperationListByModuleid(mid);
        	request.setAttribute("listOperate", operlist);
//            if(usr.getUsercode().equals("admin")){
//            	List operlist=modelService.getOperationListByModuleid(Long.valueOf(mid));
//            	request.setAttribute("listOperate", operlist);
//            }else{
//            	Pattern listPattern = Pattern.compile("mc?_"+mid+"((,o_\\d+)*)");
//            	RoleService roleService = (RoleService) wac.getBean("roleService");
//            	List<VcomSysRole> rolelist=roleService.getRoleByIds(usr.getRole());
//    			String ids="";
//    			for (VcomSysRole us : rolelist) {//组合角色权限
//    				ids=ids+us.getPermissions()+",";
//    			}
//                Matcher listMatcher = listPattern.matcher(ids);
//                while (listMatcher.find()){
//                	List operlist=modelService.getOperationListByIds(listMatcher.group(1).replaceAll("^,", "").replaceAll("o_", ""), true);
//                	request.setAttribute("listOperate", operlist);
//                }
//            }
            
        }
		return invocation.invoke();//执行调用;
	}
}
