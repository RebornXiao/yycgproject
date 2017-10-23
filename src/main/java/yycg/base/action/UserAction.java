package yycg.base.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yycg.base.dao.mapper.UsergysMapper;
import yycg.base.po.Sysuser;
import yycg.base.po.Usergys;
import yycg.base.po.Userjd;
import yycg.base.po.Useryy;
import yycg.base.process.context.Config;
import yycg.base.process.result.DataGridResultInfo;
import yycg.base.process.result.ExceptionResultInfo;
import yycg.base.process.result.ResultInfo;
import yycg.base.process.result.ResultUtil;
import yycg.base.process.result.SubmitResultInfo;
import yycg.base.service.SystemConfigService;
import yycg.base.service.UserManager;
import yycg.base.vo.PageQuery;
import yycg.base.vo.SysuserCustom;
import yycg.base.vo.SysuserQueryVo;
import yycg.util.ResourcesUtil;

@Controller
@RequestMapping("/user")//根路径 
public class UserAction {
	
	@Autowired
	UserManager userManager;
	
	@Autowired
	SystemConfigService systemConfigService;
	
	//用户列表查询页面，功能就是返回一个查询页面
	@RequestMapping("/userquery")//子路径 //http://localhost:8080/yycgproject/user/userquery.action
	public String userquery(Model model) throws Exception{
		//获取页面需要的数据，使用model传回页面
		//获取用户类型
		List grouplist = systemConfigService.findDictinfoByType("s01");
		model.addAttribute("grouplist", grouplist);
		return View.toBase("/user/userquery");
	}
	
	/**
	 * 用户列表查询数据结果集,即json格式的数据，使用注解ResponseBody表示方法返回json格式的数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userquery_result")
	public @ResponseBody DataGridResultInfo  userquery_result(
			SysuserQueryVo sysuserQueryVo,
			int page,
			int rows
			)throws Exception{
		
		//获取查询列表的总数
		int count  = userManager.findSysuserCount(sysuserQueryVo);
		
		PageQuery pageQuery = new PageQuery();
		pageQuery.setPageParams(count, rows, page);
		
		
		sysuserQueryVo.setPageQuery(pageQuery);
		
		//获取当前页的用户列表的数据
		List<SysuserCustom> list = userManager.findSysuserList(sysuserQueryVo);
		
		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
		dataGridResultInfo.setTotal(count);
		dataGridResultInfo.setRows(list);
		
		return dataGridResultInfo;
	}
	
	/**
	 * 用户添加页面
	 */
	@RequestMapping("/useradd")
	public String useradd()throws Exception{

		return "/base/user/useradd";
	}
	/**
	 * 用户添加提交
	 */
	@RequestMapping("/useraddsubmit")
	public @ResponseBody SubmitResultInfo useraddsubmit(
			SysuserQueryVo sysuserQueryVo
			)throws Exception{
		Map<String,String> test  =new HashMap<String, String>();
		
		//调用service执行添加
		
		/*try {
			userManager.insertSysuser(sysuserQueryVo.getSysuser());
		} catch (Exception e) {
			//抛出的是我们自己定义的异常
			if(e instanceof ExceptionResultInfo){
				//获取异常信息
				ResultInfo resultInfo = ((ExceptionResultInfo)e).getResultInfo();
				//return new SubmitResultInfo(resultInfo);
				return ResultUtil.createSubmitResult(resultInfo);
			}else{
				//如果不是自定义的异常,重新创建一个“未知错误”自定义异常
				//ResultInfo resultInfo  =new ResultInfo();
	        	//resultInfo.setType(ResultInfo.TYPE_RESULT_FAIL);//设置提示信息类型为失败
	        	//resultInfo.setMessage(ResourcesUtil.getValue(Config.MESSAGE, "900"));
	        	//return new SubmitResultInfo(resultInfo);
	        	//上边4行改为工具类调用
	        	return ResultUtil.createSubmitResult(ResultUtil.createFail(Config.MESSAGE, 900, null));
			}
			
		}*/
		//使用统一异常处理器接收异常
		userManager.insertSysuser(sysuserQueryVo.getSysuser());
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	/**
	 * 用户修改页面
	 */
	@RequestMapping("/useredit")
	public String  useredit(Model  model,String id) throws Exception{
		   //调用service方法获取用户信息，并将用户传到页面
		//根据用户id获取系统用户信息  
		Sysuser sysuser = userManager.getSysuserByid(id);
		
		//获取单位名称
		//根据用户类型获取不同的单位名称
		String groupid = sysuser.getGroupid();//用户类型
		String sysid = sysuser.getSysid();//单位id
		String sysmc  =null;
		if(groupid.equals("1") || groupid.equals("2")){
			//根据单位id取出单位名称
			Userjd userjd = userManager.getUserjdById(sysid);
			sysmc = userjd.getMc();//单位名称
			
		}else if(groupid.equals("3")){//医院
			Useryy useryy = userManager.getUseryyById(sysid);
			sysmc = useryy.getMc();//单位名称
		}else if(groupid.equals("4")){
			Usergys usergys = userManager.getUsergysById(sysid);
			sysmc = usergys.getMc();//单位名称
		}
		
		model.addAttribute("sysuser", sysuser);//系统用户信息
		model.addAttribute("sysmc", sysmc);//单位名称
		
		
		  return "/base/user/useredit";
	}
	/**
	 * 用户修改提交
	 */
	@RequestMapping("/usereditsubmit")
	public @ResponseBody SubmitResultInfo usereditsubmit(SysuserQueryVo sysuserQueryVo) throws Exception{
		
		if(sysuserQueryVo == null){
			sysuserQueryVo = new SysuserQueryVo();
		}
		//获取页面提交的更新对象
		Sysuser sysuser = sysuserQueryVo.getSysuser();
		
		//调用service更新用户信息
		userManager.updateSysuser(sysuser);
		
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	/**
	 * 用户删除
	 */
	@RequestMapping("/userdel")
	public @ResponseBody SubmitResultInfo userdel(String sysuserdelid) throws Exception{
		
		//调用service删除用户
		userManager.deleteSysuser(sysuserdelid);
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}


}
