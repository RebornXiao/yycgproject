Ext.onReady(function(){   
               
	
	     var mnFile=new Ext.menu.Menu({//定义一工具栏一个按钮的下拉列表Menu（注意这个item中有点乱，主要为了让大家多看几种形式）   
                    id:'mnFile',
                    minWidth : 30        //最小宽度.默认120
                });   
                
          
           var menuitem1 =  new Ext.menu.Item({   
        	   icon:'images/whole_main_curuser.gif',//设置菜单前面图标  
               text:'系统设置' ,
               handler:function(){   
               Ext.Msg.alert("系统提示","您单击了");   
               }
               }) ;
           
           
           mnFile.addItem(menuitem1);
           
           
            var blankbtn=new Ext.Button({   
                    text:'',
                    minWidth :40
                }); 
           
                var btnFile=new Ext.Button({   
                    text:'系统设置', 
                    iconCls: "hello-button",  
                    menu:mnFile   
                });  
                
                  var btnjxJ=new Ext.Button({   
                    text:'教学机配置', 
                    iconCls: "hello-button"
                });   
     
                var tb=new Ext.Toolbar({   
                    id:'tb',   
                    width:'auto',   
                    height:30
                });   
                
               // var spbtn =new Ext.ToolBar.Separator();
                var sp1 = new Ext.Toolbar.Separator();
                tb.render('divMenu');   
                var itemsinner=[];
                
       	     
               <s:iterator value="reslist" id="reslist" status="res">
            	<s:set name="id" value="%{#reslist.id}"/>
 			    <s:set name="parentid" value="%{#reslist.parentid}"/>
 			    <s:set name="text" value="%{#reslist.text}"/>
 			    <s:set name="iconCls" value="%{#reslist.iconCls}"/>
 			    <s:set name="href" value="%{#reslist.href}"/>
 			 <s:if test="#parentid==\"0\"">
            
 			  </s:if>
              <s:else>
     
                
              </s:else>
           
            </s:iterator>
                
                
                
                itemsinner[itemsinner.length]=btnFile;
                itemsinner[itemsinner.length]=new Ext.Toolbar.Separator();
                itemsinner[itemsinner.length]=btnjxJ;
                itemsinner[itemsinner.length]=new Ext.Toolbar.Separator();
               tb.addButton(itemsinner);
    
                        
            });   
           
