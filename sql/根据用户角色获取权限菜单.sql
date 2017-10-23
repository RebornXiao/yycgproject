
select m1.moduleid menuid,
       m1.name     menuname,
       m2.moduleid menuid_two,
       m2.name     menuname_two,
       m2.url
  from bss_sys_module m1, bss_sys_module m2

 where m2.parentid = m1.moduleid
   and m1.parentid = '0'
   and m2.parentid != '0'
   
   and m2.moduleid in(
   
   ---角色范围内的菜单
   select bss_sys_rolemodule.moduleid from bss_sys_role,bss_sys_rolesys,bss_sys_rolenode,bss_sys_rolemodule,bss_sys_roleoperate
   
   where bss_sys_role.roleid=bss_sys_rolesys.roleid
   and bss_sys_rolenode.rsid=bss_sys_rolesys.rsid
   and bss_sys_rolemodule.rnid=bss_sys_rolenode.rnid
   and bss_sys_roleoperate.rmid=bss_sys_rolemodule.rmid
   and bss_sys_role.roleid='A1657D9C6C7D47B59A99132A5ACE1A2E' 
   )
   order by m1.showorder,m2.showorder
   
   
   
