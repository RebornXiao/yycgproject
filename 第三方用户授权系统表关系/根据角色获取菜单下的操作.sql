--获取指定菜单的操作
select t.operateid operationId,t.method actionUrl,t.operatename operationName
  from BSS_SYS_OPERATE t

 where t.moduleid in
      --根据角色id获取二级菜单的id
       (select m1.moduleid
          from BSS_SYS_MODULE m1
        
         where m1.moduleid in
               (
                
                select bss_sys_rolemodule.moduleid
                  from bss_sys_rolesys,
                        bss_sys_rolenode,
                        bss_sys_rolemodule,
                        bss_sys_roleoperate
                
                 where bss_sys_rolesys.rsid = bss_sys_rolenode.rsid
                   and bss_sys_rolenode.rnid = bss_sys_rolemodule.rnid
                   and bss_sys_rolemodule.rmid = bss_sys_roleoperate.rmid
                   and bss_sys_rolesys.roleid =
                       'A1657D9C6C7D47B59A99132A5ACE1A2E' --卫生局的角色id
                
                )
        
        )
