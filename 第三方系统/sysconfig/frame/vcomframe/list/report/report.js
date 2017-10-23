function doPaint(id,dir,type,data,tableX,tableY,width,height,title) {
    if (data == null)return;
    outHTML = "";
	var length=0;
	var thicknessWidth=20,thicknessHeight=30;
    top_height = (title != null?20:0);
    //留置，显示标题用;
    length = thicknessWidth / 2;
   // title = (typeof(element.title) == "undefined"?null:element.title);
    if (type == "柱图")table1(dir,data,tableX,tableY,thicknessWidth,thicknessHeight,width,height,title,length,document.getElementById(id));
    else if (type == "饼图")table2(dir,data,tableX,tableY,thicknessWidth,thicknessHeight,width,height,title,length,document.getElementById(id));
    else if (type == "折线图")table3(dir,data,tableX,tableY,thicknessWidth,thicknessHeight,width,height,title,length,document.getElementById(id));
}
var line_color = "#69f";
var left_width = 40;//显示左侧文字标尺用
var top_height = 0;//留置，显示标题用;
var tb_color = [["#d1ffd1","#ffbbbb","#ffe3bb","#cff4f3","#d9d9e5","#ffc7ab","#ecffb7"],
        ["#00ff00","#ff0000","#ff9900","#33cccc","#666699","#993300","#99cc00"]];
//柱图
function table1(dir,data,tableX,tableY,thicknessWidth,thicknessHeight,width,height,title,length,element) {
    var temp1 = 0;
    var temp2,temp4;
    //取出最大值
    for (var i = 0; i < data.length; i++)
        if (temp1 < data[i].v) temp1 = data[i].v;
        //这一堆乱七八糟的计算，可能是算上面斜角呢
    if (temp1 > 9)
    {
        temp2 = temp1.toString().substr(1, 1)
        if (temp2 > 4)
            temp3 = (parseInt((temp1 / (Math.pow(10, (temp1.toString().length - 1)))).toString()) + 1) * Math.pow(10, (temp1.toString().length - 1));
        else
            temp3 = (parseInt((temp1 / (Math.pow(10, (temp1.toString().length - 1)))).toString()) + 0.5) * Math.pow(10, (temp1.toString().length - 1));
    }
    else
    {
        if (temp1 > 4)
            temp3 = 10;
        else
            temp3 = 5;
    }
    temp4 = temp3;
    if (title != null) {
        //outHTML += "<!--[if gte vml 1]><v:rect id='_x0000_s1027' alt='' style='position:absolute;left:" + (tableX + left_width + 20) + "px;top:" + (tableY) + "px;width:" + (width - 60) + "px;height:" + 30 + "px;z-index:-1' filled='f' stroked='f'><center>" + title + "</center></v:rect><![endif]-->";
    }
    outHTML += "<!--[if gte vml 1]><v:rect id='_x0000_s1027' alt='' style='position:absolute;left:" + (tableX + left_width) + "px;top:" + (tableY + top_height) + "px;width:" + width + "px;height:" + height + "px;z-index:-1' fillcolor='#9cf' stroked='f'><v:fill rotate='t' angle='-45' focus='100%' type='gradient'/></v:rect><![endif]-->";
    outHTML += "<!--[if gte vml 1]><v:line id='_x0000_s1027' alt='' style='position:absolute;left:0;text-align:left;top:0;flip:y;z-index:-1' from='" + (tableX + left_width) + "px," + ((tableY + top_height) + height) + "px' to='" + (tableX + width + left_width) + "px," + ((tableY + top_height) + height) + "px'/><![endif]-->";
    outHTML += "<!--[if gte vml 1]><v:line id='_x0000_s1027' alt='' style='position:absolute;left:0;text-align:left;top:0;flip:y;z-index:-1' from='" + (tableX + left_width) + "px," + (tableY + top_height) + "px' to='" + (tableX + left_width) + "px," + ((tableY + top_height) + height) + "px'/><![endif]-->";
    //两种对齐方法的不同绘制
    if (dir == "水平") {
        var table_space = (width - thicknessHeight * data.length) / data.length;
        outHTML += "<!--[if gte vml 1]><v:line id='_x0000_s1027' alt='' style='position:absolute;left:0;text-align:left;top:0;flip:y;z-index:-1' from='" + (tableX + left_width + length) + "px," + (tableY + top_height) + "px' to='" + (tableX + left_width + length) + "px," + ((tableY + top_height) + height - length) + "px' strokecolor='" + line_color + "'/><![endif]-->";
        for (var i = 0; i <= height - 1; i += height / 5)
        {
            outHTML += "<!--[if gte vml 1]><v:line id='_x0000_s1027' alt='' style='position:absolute;left:0;text-align:left;top:0;flip:y;z-index:-1' from='" + (tableX + left_width) + "px," + ((tableY + top_height) + height - length - i) + "px' to='" + (tableX + left_width + length) + "px," + ((tableY + top_height) + height - i) + "px' strokecolor='" + line_color + "'/><![endif]-->";
            outHTML += "<!--[if gte vml 1]><v:line id='_x0000_s1027' alt='' style='position:absolute;left:0;text-align:left;top:0;flip:y;z-index:-1' from='" + (tableX + left_width + length) + "px," + ((tableY + top_height) + height - length - i) + "px' to='" + (tableX + width + left_width) + "px," + ((tableY + top_height) + height - length - i) + "px' strokecolor='" + line_color + "'/><![endif]-->";
            outHTML += "<!--[if gte vml 1]><v:line id='_x0000_s1027' alt='' style='position:absolute;left:0;text-align:left;top:0;flip:y;z-index:-1' from='" + (tableX + (left_width - 15)) + "px," + ((tableY + top_height) + i) + "px' to='" + (tableX + left_width) + "px," + ((tableY + top_height) + i) + "px'/><![endif]-->";
            outHTML += "<!--[if gte vml 1]>";
            outHTML += "<v:shape id='_x0000_s1025' type='#_x0000_t202' alt='' style='position:absolute;left:" + tableX + "px;top:" + ((tableY + top_height) + i) + "px;width:" + left_width + "px;height:18px;z-index:1'>";
            outHTML += "<v:textbox inset='0px,0px,0px,0px'><table cellspacing='3' cellpadding='0' width='100%' height='100%'><tr><td align='right'>" + temp4 + "</td></tr></table></v:textbox></v:shape><![endif]-->";
            temp4 = temp4 - temp3 / 5;
        }

        for (var i = 0; i < data.length; i++)
        {
            var temp_space = tableX + left_width + table_space / 2 + table_space * i + thicknessHeight * i;
            outHTML += "<v:rect id='_x0000_s1025' alt='' style='position:absolute;left:";
            outHTML += temp_space;
            outHTML += "px;top:";
            outHTML += (tableY + top_height) + height * (1 - (data[i].v / temp3));
            outHTML += "px;width:" + thicknessHeight + "px;height:" + height * (data[i].v / temp3) + "px;z-index:1' fillcolor='" + tb_color[1][i] + "'>";
            outHTML += "<v:fill color2='" + tb_color[0][i] + "' rotate='t' type='gradient'/>";
            outHTML += "<o:extrusion v:ext='view' backdepth='" + thicknessWidth + "pt' color='" + tb_color[1][i] + "' on='t'/>";
            outHTML += "</v:rect>";
            outHTML += "<v:shape id='_x0000_s1025' type='#_x0000_t202' alt='' style='position:absolute;left:" + temp_space + "px;top:" + ((tableY + top_height) + height * (1 - (data[i].v / temp3)) - thicknessHeight) + "px;width:" + (table_space + 15) + "px;height:18px;z-index:1'>";
            outHTML += "<v:textbox inset='0px,0px,0px,0px'><table cellspacing='3' cellpadding='0' width='100%' height='100%'><tr><td align='center'>" + data[i].v + "</td></tr></table></v:textbox></v:shape>";
            outHTML += "<v:shape id='_x0000_s1025' type='#_x0000_t202' alt='' style='position:absolute;left:" + (temp_space - table_space / 2) + "px;top:" + ((tableY + top_height) + height + 1) + "px;width:" + (table_space + thicknessHeight) + "px;height:18px;z-index:1'>";
            outHTML += "<v:textbox inset='0px,0px,0px,0px'><table cellspacing='3' cellpadding='0' width='100%' height='100%'><tr><td align='center'>" + data[i].n + "</td></tr></table></v:textbox></v:shape>";
        }
    } else {//垂直
        var table_space = (height - thicknessHeight * data.length) / data.length;
        outHTML += "<!--[if gte vml 1]><v:line id='_x0000_s1027' alt='' style='position:absolute;left:0;text-align:left;top:0;flip:y;z-index:-1' from='" + (tableX + left_width + length) + "px," + ((tableY + top_height) + height - length) + "px' to='" + (tableX + left_width + width) + "px," + ((tableY + top_height) + height - length) + "px' strokecolor='" + line_color + "'/><![endif]-->";
        for (var i = 0; i <= width - 1; i += width / 5) {
            outHTML += "<!--[if gte vml 1]><v:line id='_x0000_s1027' alt='' style='position:absolute;left:0;text-align:left;top:0;flip:y;z-index:-1' from='" + (tableX + left_width + i) + "px," + ((tableY + top_height) + height - length) + "px' to='" + (tableX + left_width + length + i) + "px," + ((tableY + top_height) + height) + "px' strokecolor='" + line_color + "'/><![endif]-->";
            outHTML += "<!--[if gte vml 1]><v:line id='_x0000_s1027' alt='' style='position:absolute;left:0;text-align:left;top:0;flip:y;z-index:-1' from='" + (tableX + left_width + length + i) + "px," + ((tableY + top_height) + height - length) + "px' to='" + (tableX + left_width + length + i) + "px," + (tableY + top_height) + "px' strokecolor='" + line_color + "'/><![endif]-->";
            outHTML += "<!--[if gte vml 1]><v:line id='_x0000_s1027' alt='' style='position:absolute;left:0;text-align:left;top:0;flip:y;z-index:-1' from='" + (tableX + left_width + i + width / 5) + "px," + ((tableY + top_height) + height) + "px' to='" + (tableX + left_width + i + width / 5) + "px," + ((tableY + top_height) + height + 15) + "px'/><![endif]-->";
            outHTML += "<!--[if gte vml 1]>";
            outHTML += "<v:shape id='_x0000_s1025' type='#_x0000_t202' alt='' style='position:absolute;left:" + (tableX - left_width + (width - i) + width / 5 - left_width) + "px;top:" + ((tableY + top_height) + height) + "px;width:" + left_width + "px;height:18px;z-index:1'>";
            outHTML += "<v:textbox inset='0px,0px,0px,0px'><table cellspacing='3' cellpadding='0' width='100%' height='100%'><tr><td align='right'>" + temp4 + "</td></tr></table></v:textbox></v:shape><![endif]-->";
            temp4 = temp4 - temp3 / 5;
        }

        for (var i = 0; i < data.length; i++) {
            var temp_space = table_space / 2 + table_space * i + thicknessHeight * i;
            outHTML += "<v:rect id='_x0000_s1025' alt='' style='position:absolute;left:";
            outHTML += tableX + left_width;
            outHTML += "px;top:";
            outHTML += (tableY + top_height) + temp_space;
            outHTML += "px;width:" + width * (data[i].v / temp3) + "px;height:" + thicknessHeight + "px;z-index:1' fillcolor='" + tb_color[1][i] + "'>";
            outHTML += "<v:fill color2='" + tb_color[0][i] + "' rotate='t' angle='-90' focus='100%' type='gradient'/>";
            outHTML += "<o:extrusion v:ext='view' backdepth='" + thicknessWidth + "pt' color='" + tb_color[1][i] + "' on='t'/>";
            outHTML += "</v:rect>";
            outHTML += "<v:shape id='_x0000_s1025' type='#_x0000_t202' alt='' style='position:absolute;left:" + (tableX + left_width + width * (data[i].v / temp3) + thicknessWidth / 2) + "px;top:" + ((tableY + top_height) + temp_space) + "px;width:" + (table_space + left_width) + "px;height:18px;z-index:1'>";
            outHTML += "<v:textbox inset='0px,0px,0px,0px'><table cellspacing='3' cellpadding='0' width='100%' height='100%'><tr><td align='center'>" + data[i].v + "</td></tr></table></v:textbox></v:shape>";

            outHTML += "<v:shape id='_x0000_s1025' type='#_x0000_t202' alt='' style='position:absolute;left:" + tableX + "px;top:" + ((tableY + top_height) + temp_space) + "px;width:" + left_width + "px;height:18px;z-index:1'>";
            outHTML += "<v:textbox inset='0px,0px,0px,0px'><table cellspacing='3' cellpadding='0' width='100%' height='100%'><tr><td align='right'>" + data[i].n + "</td></tr></table></v:textbox></v:shape>";
        }

    }
    element.innerHTML = outHTML;
}
//饼图
function table2(dir,data,tableX,tableY,thicknessWidth,thicknessHeight,width,height,title,length,element) {
    var totalpie = 0;
    var tb_height = 30;
    for (var i = 0; i < data.length; i++)totalpie += data[i].v;
    var PreAngle = 0;
    for (var i = 0; i < data.length; i++) {
        outHTML += "<v:shape id='_x0000_s1025' alt='' style='position:absolute;left:" + tableX + "px;top:" + tableY + "px;width:" + width + "px;height:" + height + "px;z-index:1' coordsize='1500,1400' o:spt='100' adj='0,,0' path='m750,700ae750,700,750,700," + parseInt(23592960 * PreAngle) + "," + parseInt(23592960 * data[i].v / totalpie) + "xe' fillcolor='" + tb_color[0][i] + "' strokecolor='#FFFFFF'><v:fill color2='" + tb_color[1][i] + "' rotate='t' focus='100%' type='gradient'/><v:stroke joinstyle='round'/><v:formulas/><v:path o:connecttype='segments'/></v:shape>";
        PreAngle += data[i].v / totalpie;
    }
    if (dir == "图例") {
        outHTML += "<v:rect id='_x0000_s1025' style='position:absolute;left:" + (tableX + width + 20) + "px;top:" + (tableY + 20) + "px;width:100px;height:" + (data.length * tb_height + 20) + "px;z-index:1'/>";
        for (var i = 0; i < data.length; i++)
        {
            outHTML += "<v:shape id='_x0000_s1025' type='#_x0000_t202' alt='' style='position:absolute;left:" + (tableX + width + 25) + "px;top:" + (tableY + 30 + (i) * tb_height) + "px;width:60px;height:" + tb_height + "px;z-index:1'>";
            outHTML += "<v:textbox inset='0px,0px,0px,0px'><table cellspacing='3' cellpadding='0' width='100%' height='100%'><tr><td align='left'>" + data[i].n + "</td></tr></table></v:textbox></v:shape>";
            outHTML += "<v:rect id='_x0000_s1040' alt='' style='position:absolute;left:" + (tableX + width + 80) + "px;top:" + (tableY + 30 + (i) * tb_height + 3) + "px;width:30px;height:20px;z-index:1' fillcolor='" + tb_color[0][i] + "'><v:fill color2='" + tb_color[1][i] + "' rotate='t' focus='100%' type='gradient'/></v:rect>";
        }

    } else if (dir == "百分比") {

        var pie = 3.14159265358979;
        var TempPie = 0;
        for (var i = 0; i < data.length; i++)
        {
            var TempAngle = pie * 2 * (data[i].v / (totalpie * 2) + TempPie);
            var x1 = tableX + width / 2 + Math.cos(TempAngle) * width * 3 / 8;
            var y1 = tableY + height / 2 - Math.sin(TempAngle) * height * 3 / 8;
            var x2 = tableX + width / 2 + Math.cos(TempAngle) * width * 3 / 4;
            var y2 = tableY + height / 2 - Math.sin(TempAngle) * height * 3 / 4;

            if (x2 > tableX + width / 2)
            {
                x3 = x2 + 20;
                x4 = x3;
            }
            else
            {
                x3 = x2 - 20;
                x4 = x3 - 100;
            }
            outHTML += "<v:oval id='_x0000_s1027' style='position:absolute;left:" + (x1 - 2) + "px;top:" + (y1 - 2) + "px;width:4px;height:4px; z-index:2' fillcolor='#111111' strokecolor='#111111'/>";
            outHTML += "<v:line id='_x0000_s1025' alt='' style='position:absolute;left:0;text-align:left;top:0;z-index:1' from='" + x1 + "px," + y1 + "px' to='" + x2 + "px," + y2 + "px' coordsize='21600,21600' strokecolor='#111111' strokeweight='1px'></v:line>";
            outHTML += "<v:line id='_x0000_s1025' alt='' style='position:absolute;left:0;text-align:left;top:0;z-index:1' from='" + x2 + "px," + y2 + "px' to='" + x3 + "px," + y2 + "px' coordsize='21600,21600' strokecolor='#111111' strokeweight='1px'></v:line>";
            outHTML += "<v:shape id='_x0000_s1025' type='#_x0000_t202' alt='' style='position:absolute;left:" + x4 + "px;top:" + (y2 - 10) + "px;width:100px;height:20px;z-index:1'>";
            outHTML += "<v:textbox inset='0px,0px,0px,0px'><table cellspacing='3' cellpadding='0' width='100%' height='100%'><tr><td align='left'>" + data[i].n + " " + Math.round(parseFloat(data[i].v * 100 / totalpie) * 100) / 100 + "%</td></tr></table></v:textbox></v:shape>"
            TempPie += data[i].v / totalpie;
        }
    }
    element.innerHTML = outHTML;
}
//折线图
function table3(dir,data,tableX,tableY,thicknessWidth,thicknessHeight,width,height,title,length,element) {
    var temp1,temp2,temp3;
    temp1 = 0;
    data = data[0];
    var total_no = data.fields.length;
    //取最大值
    for (var i = 0; i < data.data.length; i++) {
        for (var j = 0; j < data.fields.length; j++) if (temp1 < data.data[i].v[j]) temp1 = data.data[i].v[j];
    }
    if (temp1 > 9)
    {
        temp2 = temp1.toString().substr(1, 1);
        if (temp2 > 4)
            temp3 = (parseInt(temp1 / (Math.pow(10, (temp1.toString().length - 1)))) + 1) * Math.pow(10, (temp1.toString().length - 1));
        else
            temp3 = (praseInt(temp1 / (Math.pow(10, (temp1.toString().length - 1)))) + 0.5) * Math.pow(10, (temp1.toString().length - 1))
    }
    else
    {
        if (temp1 > 4)
            emp3 = 10;
        else
            temp3 = 5;
    }
    temp4 = temp3;

    outHTML += "<v:rect id='_x0000_s1027' alt='' style='position:absolute;left:" + (tableX + left_width) + "px;top:" + tableY + "px;width:" + width + "px;height:" + height + "px;z-index:-1' fillcolor='#9cf' stroked='f'><v:fill rotate='t' angle='-45' focus='100%' type='gradient'/></v:rect>";

    for (var i = 0; i < height; i += height / 5)
    {
        outHTML += "<v:line id='_x0000_s1027' alt='' style='position:absolute;left:0;text-align:left;top:0;flip:y;z-index:-1' from='" + (tableX + left_width + length) + "px," + (tableY + height - length - i) + "px' to='" + (tableX + width + left_width) + "px," + (tableY + height - length - i) + "px' strokecolor='" + line_color + "'/>";
        outHTML += "<v:line id='_x0000_s1027' alt='' style='position:absolute;left:0;text-align:left;top:0;flip:y;z-index:-1' from='" + (tableX + (left_width - 15)) + "px," + (tableY + i) + "px' to='" + (tableX + left_width) + "px," + (tableY + i) + "px'/>";
        outHTML += "<v:shape id='_x0000_s1025' type='#_x0000_t202' alt='' style='position:absolute;left:" + tableX + "px;top:" + (tableY + i) + "px;width:" + left_width + "px;height:18px;z-index:1'>";
        outHTML += "<v:textbox inset='0px,0px,0px,0px'><table cellspacing='3' cellpadding='0' width='100%' height='100%'><tr><td align='right'>" + temp4 + "</td></tr></table></v:textbox></v:shape>";
        temp4 = temp4 - temp3 / 5;
    }
    outHTML += "<v:line id='_x0000_s1027' alt='' style='position:absolute;left:0;text-align:left;top:0;flip:y;z-index:-1' from='" + (tableX + left_width) + "px," + (tableY + height) + "px' to='" + (tableX + width + left_width) + "px," + (tableY + height) + "px'/>";
    outHTML += "<v:line id='_x0000_s1027' alt='' style='position:absolute;left:0;text-align:left;top:0;flip:y;z-index:-1' from='" + (tableX + left_width) + "px," + tableY + "px' to='" + (tableX + left_width) + "px," + (tableY + height) + "px'/>";

    for (var j = 1; j <= data.data.length; j++)
    {
        for (var i = 1; i < data.fields.length; i++)
        {
            var x1 = tableX + left_width + width * (i - 1) / (total_no);
            var y1 = tableY + (temp3 - data.data[j - 1].v[i - 1]) * (height / temp3)
            var x2 = tableX + left_width + width * i / (total_no)
            var y2 = tableY + (temp3 - data.data[j - 1].v[i]) * (height / temp3)

            outHTML += "<v:line id='_x0000_s1025' alt='' style='position:absolute;left:0;text-align:left;top:0;z-index:1' from='" + x1 + "px," + y1 + "px' to='" + x2 + "px," + y2 + "px' coordsize='21600,21600' strokecolor='" + data.data[j - 1].c + "' strokeweight='" + data.data[j - 1].w + "'>";

            switch (data.data[j - 1].t)
                    {
                case 1:
                    break;
                case 2:
                    outHTML += "<v:stroke dashstyle='1 1'/>";
                    break;
                case 3:
                    outHTML += "<v:stroke dashstyle='dash'/>";
                    break;
                case 4:
                    outHTML += "<v:stroke dashstyle='dashDot'/>";
                    break;
                case 5:
                    outHTML += "<v:stroke dashstyle='longDash'/>";
                    break;
                case 6:
                    outHTML += "<v:stroke dashstyle='longDashDot'/>";
                    break;
                case 7:
                    outHTML += "<v:stroke dashstyle='longDashDotDot'/>";
                    break;
            }
            outHTML += "</v:line>";
            switch (data.data[j - 1]._t) {
                case 1:
                    break;
                case 2:
                    outHTML += "<v:rect id='_x0000_s1027' style='position:absolute;left:" + (x1 - 2) + "px;top:" + (y1 - 2) + "px;width:4px;height:4px; z-index:2' fillcolor='" + data.data[j - 1].c + "' strokecolor='" + data.data[j - 1].c + "'/>";
                    break;
                case 3:
                    outHTML += "<v:oval id='_x0000_s1026' style='position:absolute;left:" + (x1 - 2) + "px;top:" + (y1 - 2) + "px;width:4px;height:4px;z-index:1' fillcolor='" + data.data[j - 1].c + "' strokecolor='" + data.data[j - 1].c + "'/>";
                    break;
            }
        }
        switch (data.data[j - 1]._t)
                {
            case 1:
                break;
            case 2:
                outHTML += "<v:rect id='_x0000_s1027' style='position:absolute;left:" + (x2 - 2) + "px;top:" + (y2 - 2) + "px;width:4px;height:4px; z-index:2' fillcolor='" + data.data[j - 1].c + "' strokecolor='" + data.data[j - 1].c + "'/>";
                break;
            case 3:
                outHTML += "<v:oval id='_x0000_s1026' style='position:absolute;left:" + (x2 - 2) + "px;top:" + (y2 - 2) + "px;width:4px;height:4px;z-index:1' fillcolor='" + data.data[j - 1].c + "' strokecolor='" + data.data[j - 1].c + "'/>";
                break;
        }
    }
    for (var i = 0; i < total_no; i++)
    {
        outHTML += "<v:line id='_x0000_s1027' alt='' style='position:absolute;left:0;text-align:left;top:0;flip:y;z-index:-1' from='" + (tableX + left_width + width * (i) / (total_no)) + "px," + (tableY + height) + "px' to='" + (tableX + left_width + width * (i) / (total_no)) + "px," + (tableY + height + 15) + "px'/>";
        outHTML += "<v:shape id='_x0000_s1025' type='#_x0000_t202' alt='' style='position:absolute;left:" + (tableX + left_width + width * (i) / (total_no)) + "px;top:" + (tableY + height) + "px;width:" + (width / (total_no)) + "px;height:18px;z-index:1'>";
        outHTML += "<v:textbox inset='0px,0px,0px,0px'><table cellspacing='3' cellpadding='0' width='100%' height='100%'><tr><td align='left'>" + data.fields[i] + "</td></tr></table></v:textbox></v:shape>";
    }
    var tb_height = 30;
    outHTML += "<v:rect id='_x0000_s1025' style='position:absolute;left:" + (tableX + width + 20) + "px;top:" + tableY + "px;width:100px;height:" + (data.data.length * tb_height + 20) + "px;z-index:1'/>";
    for (var i = 0; i < data.data.length; i++)
    {
        outHTML += "<v:shape id='_x0000_s1025' type='#_x0000_t202' alt='' style='position:absolute;left:" + (tableX + width + 25) + "px;top:" + (tableY + 10 + (i) * tb_height) + "px;width:60px;height:" + tb_height + "px;z-index:1'>";
        outHTML += "<v:textbox inset='0px,0px,0px,0px'><table cellspacing='3' cellpadding='0' width='100%' height='100%'><tr><td align='left'>" + data.data[i].n + "</td></tr></table></v:textbox></v:shape>";
        outHTML += "<v:rect id='_x0000_s1040' alt='' style='position:absolute;left:" + (tableX + width + 80) + "px;top:" + (tableY + 10 + (i) * tb_height + 4) + "px;width:30px;height:20px;z-index:1' fillcolor='" + data.data[i].c + "'><v:fill color2='" + data.data[i].c + "' rotate='t' focus='100%' type='gradient'/></v:rect>";
    }
    element.innerHTML = outHTML;
}