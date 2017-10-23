select m1.moduleid,
       m1.name modulename,
       m2.moduleid moduleid_child,
       m2.name modulename_child,
       m2.url
  from BSS_SYS_MODULE m1
  left join BSS_SYS_MODULE m2
    on m1.moduleid = m2.parentid
 where m1.parentid = '0'
   and m2.parentid != '0'
   --获取某个用户角色下的菜单
   and m2.moduleid in(
   
   
   select bss_sys_rolemodule.moduleid from bss_sys_rolesys,bss_sys_rolenode,bss_sys_rolemodule,bss_sys_roleoperate

where bss_sys_rolesys.rsid = bss_sys_rolenode.rsid
and bss_sys_rolenode.rnid = bss_sys_rolemodule.rnid
and bss_sys_rolemodule.rmid = bss_sys_roleoperate.rmid
and bss_sys_rolesys.roleid='A1657D9C6C7D47B59A99132A5ACE1A2E'--卫生局的角色id
   
   ) 
   
order by m1.showorder,m2.showorder

