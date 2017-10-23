select bss_sys_operate.method      actionUrl,
       bss_sys_operate.operatename operationName,
       bss_sys_operate.operateid operationId
  from bss_sys_operate

 where bss_sys_operate.operateid in
       (
        
        --角色范围内的操作权限集合
        select bss_sys_roleoperate.operateid
          from bss_sys_role,
                bss_sys_rolesys,
                bss_sys_rolenode,
                bss_sys_rolemodule,
                bss_sys_roleoperate
        
         where bss_sys_role.roleid = bss_sys_rolesys.roleid
           and bss_sys_rolenode.rsid = bss_sys_rolesys.rsid
           and bss_sys_rolemodule.rnid = bss_sys_rolenode.rnid
           and bss_sys_roleoperate.rmid = bss_sys_rolemodule.rmid
           and bss_sys_role.roleid = 'A1657D9C6C7D47B59A99132A5ACE1A2E'
        
        )
