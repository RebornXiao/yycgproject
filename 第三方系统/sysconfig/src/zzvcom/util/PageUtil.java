package zzvcom.util;

/**
 * 
 * @author Administrator
 * 
 */
public class PageUtil {
	private int curPage = 1; // 当前页
	private int pageSize = 15; // 每页多少行
    private int totalRow; // 共多少行
    
    
    private int start=0;
	private int end=10;
	private int rowend=0;
	private int totalPage; // 共多少页

	public int getRowend() {
		return rowend;
	}
	public int getCurPage() {
		return curPage; 
	}
	public PageUtil(int page){
		start = pageSize * (page - 1);
		end=pageSize ;
		this.curPage = page;
		curPage=(curPage<=0?1:curPage);
		rowend = curPage<=0?(pageSize):curPage*pageSize;
	}
	public PageUtil(){};
	public void setCurPage(int curPage) {
		start = pageSize * (curPage - 1);
		end=pageSize ;
		curPage=(curPage<=0?1:curPage);
		rowend = curPage*pageSize;
		this.curPage = curPage;
	}
	
	public int getStart() {
        //start=curPage*pageSize;
        return start;
	}



	public int getEnd() {

        return end;
	}
	public int getOracleStart(){
		return this.getStart()+1;
	}
	public int getOracleEnd(){
		return this.getCurPage()*this.getPageSize();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
        totalPage= (totalRow+pageSize-1)/pageSize;
        this.totalRow = totalRow;
        curPage=(curPage<=0?1:curPage);
		if(totalPage<curPage) {
			curPage=totalPage;
			start = pageSize * (curPage - 1);
			end=pageSize;
			rowend = curPage<=0?(pageSize):curPage*pageSize;
		}
		if(totalPage == 0){
			curPage=1;
			start = 0;
			end=totalRow;
			rowend = curPage<=0?(pageSize):curPage*pageSize;
		}	
	}

	public int getTotalPage() {

		return this.totalPage;
	}
	
	public String getToolsMenu() {
		StringBuffer str = new StringBuffer("");
		int next, prev;
		prev = curPage - 1;
		next = curPage + 1;
		str.append("页次："+curPage+"/"+totalPage+"  共"+totalRow+"条  ");
		if (curPage > 1) {
			str.append("<SPAN class=grid-sp-c onclick='document.forms(0).pageIndex.value=1;document.forms(0).submit();'>9</SPAN>&nbsp;");
		} else {
			str.append(" <SPAN class=grid-sp-c>9</SPAN>&nbsp;");
		}
		if (curPage > 1) {
			str.append(" <SPAN class=grid-sp-c onclick='document.forms(0).pageIndex.value=" + prev
					+ ";document.forms(0).submit();'>3</SPAN>&nbsp;");
		} else {
			str.append(" <SPAN class=grid-sp-c>3</SPAN> &nbsp;");
		}
		if (curPage < totalPage) {
			str.append("<SPAN class=grid-sp-c onclick='document.forms(0).pageIndex.value=" + next
					+ ";document.forms(0).submit();'>4</SPAN>&nbsp;");
		} else {
			str.append(" <SPAN class=grid-sp-c>4</SPAN>&nbsp;");
		}
		if (totalPage > 1 && curPage != totalPage) {
			str.append("<SPAN class=grid-sp-c onclick='document.forms(0).pageIndex.value=" + totalPage
					+ ";document.forms(0).submit();'>:</SPAN>&nbsp");
		} else {
			str.append(" <SPAN class=grid-sp-c>:</SPAN>&nbsp;");
		}
		/*
		str.append(" 共" + totalRow + "条记录");
		str
				.append("  每页<SELECT size=1 name=pagesize onchange='this.form.pages.value=1;this.form.pageSize.value=this.value;this.form.submit();'>");

		if (pageSize == 3) {
			str.append("<OPTION value=3 selected>3</OPTION>");
		} else {
			str.append("<OPTION value=3>3</OPTION>");
		}

		if (pageSize == 10) {
			str.append("<OPTION value=10 selected>10</OPTION>");
		} else {
			str.append("<OPTION value=10>10</OPTION>");
		}
		if (pageSize == 20) {
			str.append("<OPTION value=20 selected>20</OPTION>");
		} else {
			str.append("<OPTION value=20>20</OPTION>");
		}
		if (pageSize == 50) {
			str.append("<OPTION value=50 selected>50</OPTION>");
		} else {
			str.append("<OPTION value=50>50</OPTION>");
		}
		if (pageSize == 100) {
			str.append("<OPTION value=100 selected>100</OPTION>");
		} else {
			str.append("<OPTION value=100>100</OPTION>");
		}
		str.append("</SELECT>");
		str.append("条" );
		*/
		str.append(" 跳转到第");
		str.append("<input type='text' id=gotopage size=3 />页<input type=button value='GO' onclick=\"if(!isNaN(parseInt(document.getElementById('gotopage').value))){this.form.pageIndex.value=document.getElementById('gotopage').value;this.form.submit();}else{alert('请输入数字');}\"/>");
		/*
		str.append("<SELECT size=1 name=Pagelist onchange='this.form.pageIndex.value=this.value;this.form.submit();' style=WIDTH: 40px>");
		int begin=totalPage<=10?1:(curPage<=5?1:(curPage-5)),end=totalPage<10?totalPage:(curPage<=5?10:(totalPage-curPage<=5?totalPage:(curPage+4)));
		
		for (int i = begin; i <= end; i++) {
			if (i == curPage) {
				str.append("<OPTION value=" + i + " selected>" + i + "</OPTION>");
			} else {
				str.append("<OPTION value=" + i + ">" + i + "</OPTION>");
			}
		}
		str.append("</SELECT>页");
		*/
		str.append("<INPUT type=hidden  value=" + curPage + " name=\"pageIndex\" > ");
		return str.toString();
	}
}
