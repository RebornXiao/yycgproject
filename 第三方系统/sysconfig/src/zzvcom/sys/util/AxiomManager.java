/**
 * @公司名称：VCOM
 * @文件名：AxiomManager.java
 * @作者：夏全刚
 * @版本号：1.0
 * @生成日期：2008-10-15
 * @功能描述：
 */
package zzvcom.sys.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMDocument;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

public class AxiomManager
{
   // private LogManager logger =  LogManager.getMyLog(this.getClass());
    /**
     * 取xpath节点和子结点值列表
     * 
     * @param om 节点构造器
     * @param xpath XPath 节点路径
     * @return 返回节点数据 节点列表用Vector存放 每个节点用HashMap存放
     * @throws DocumentException 
     */
    public Vector<HashMap> getNodeList(OMElement om, String xpath) throws Exception
    {
        try{
            Document doc;
            doc = DocumentHelper.parseText(om.toString());
            Vector v = new Vector<HashMap>();
            HashMap<String,String> map = null;
            List list = doc.selectNodes(xpath);
            Iterator iter = list.iterator();
            while (iter.hasNext())
            {
                Element ele = (Element) iter.next();
                Iterator sub = ele.elementIterator();
                map = new HashMap<String,String>();
                while (sub.hasNext())
                {
                    Element subele = (Element) sub.next();
                    map.put(subele.getName(), subele.getText());
                }
                v.add(map);
            }
            return v;
        }catch(Exception e)
        {
            throw new MyException(this,"getNodeList",e.getMessage());
        }
    }

    /**
     * 将节点列表的数据封装到Vector中
     * 
     * @param iter 节点列表
     * @return 封装后的数据
     */
    public Vector<HashMap> addSubEle(Iterator iter)
    {
        Vector v = new Vector();
        HashMap map = null;
        while (iter.hasNext())
        {
            Element ele = (Element) iter.next();
            Iterator sub = ele.elementIterator();
            map = new HashMap<String,String>();
            while (sub.hasNext())
            {
                Element subele = (Element) sub.next();
                Iterator subchild = subele.elementIterator();
                if (subchild.hasNext())
                {
                    Vector v_sub = addSubEle(subchild);
                    map.put(subele.getName(), v_sub);
                }
                else
                {
                    map.put(subele.getName(), subele.getText());
                }
            }
            v.add(map);
        }
        return v;
    }

    /**
     * 取xpath节点和子结点值列表
     * 
     * @param om 节点构造器
     * @param xpath XPath 节点路径
     * @return 返回节点数据 节点列表用Vector存放 每个节点用HashMap存放
     * @throws DocumentException 
     * @throws DocumentException 
     */
    public Vector<HashMap> getNodeList(String om, String xpath) throws Exception
    {
        try{
            Document doc;
            doc = DocumentHelper.parseText(om);
            Vector v = new Vector<HashMap>();
            List list = doc.selectNodes(xpath);
            Iterator iter = list.iterator();
            v = addSubEle(iter);
            return v;
        }catch(Exception e)
        {
            throw new MyException(this,"getNodeList",e.getMessage());
        }
    }

    /**
     * 取的xpath节点的值
     * 
     * @param om 节点构造器
     * @param xpath XPath节点路径
     * @return 节点值
     * @throws DocumentException 
     */
    public String getSingleNode(OMElement om, String xpath) throws Exception
    {
        try{
            Document doc;
            doc = DocumentHelper.parseText(om.toString());
            Node node = doc.selectSingleNode(xpath);
            return node.getText();
            }catch(Exception e)
        {
            throw new MyException(this,"getNodeList",e.getMessage());
        }
    }

    /**
     * 取的xpath节点的值
     * 
     * @param om 节点构造器
     * @param xpath XPath节点路径
     * @return 节点值
     * @throws DocumentException 
     */
    public String getSingleNode(String om, String xpath) throws Exception
    {
        try{
        Document doc;
        doc = DocumentHelper.parseText(om);
        Node node = doc.selectSingleNode(xpath);
        if (node != null)
            return node.getText();
        else
            return "";
        }catch(Exception e)
        {
            throw new MyException(this,"getNodeList",e.getMessage());
        }
    }

    /**
     * 构造xml格式的数据
     * 
     * @param nodeName 节点标签名称
     * @param v 构造好的节点数据
     * @param isLeaf 是否添加节点列表名称
     * @return 节点构造器
     */

    public OMElement constuctXML(String rootName,HashMap map)
    {
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMDocument doc = factory.createOMDocument();
        OMNamespace ns = factory.createOMNamespace("", "");

        String[] subName = {};
        //String rootName = "VCOM";
        OMElement root = factory.createOMElement(rootName, ns);
        if (map != null && !map.isEmpty())
        {
            OMElement om = addNode(factory, root, map);
        }
        doc.addChild(root);
      //  logger.info("封装后的xml格式数据>>>>\n" + root.toString());
        return root;
    }

    /**
     * 在传入节点上添加子结点
     * 
     * @param nodeName 多节点标签名称
     * @param factory 节点构造器工厂
     * @param pom 节点构造器
     * @param v 节点数据
     * @param isLeaf 是否添加节点列表名称
     */

    public void addNode(String nodeName, OMFactory factory, OMElement pom,
            Vector v)
    {
        HashMap map = null;
        OMElement som = null;
        OMElement om = null;
        boolean hashV = false;
        int vnum = v.size();
        for (int i = 0; i < vnum; i++)
        {
            om = factory.createOMElement(nodeName, null);
            map = (HashMap) v.get(i);
            om = addNode(factory, om, map);
            pom.addChild(om);
        }// for

    }

    /**
     * 根据传入的数据包封装成xml节点
     * 
     * @param factory 节点构造器工厂
     * @param om 节点构造器
     * @param map 封装的数据
     * @return 封装后的节点构造器
     */
    public OMElement addNode(OMFactory factory, OMElement om, HashMap map)
    {
        Iterator it = map.entrySet().iterator();
        while (it.hasNext())
        {
            String[] value = {};
            OMElement som = null;
            Map.Entry emap = (Map.Entry) it.next();
            som = factory.createOMElement(emap.getKey().toString(), null);
            String type = "";
            if (emap.getValue() != null)
                type = emap.getValue().getClass().getSimpleName();
            else
                type = "String";
            if (emap.getValue() != null)
            {
                if (type.equals("Vector"))
                {
                    Vector vvv = (Vector) (emap.getValue());
                    som = factory.createOMElement(emap.getKey().toString()
                            + "s", null);
                    addNode(emap.getKey().toString(), factory, som, vvv);
                } //
                else if (type.equals("HashMap"))
                {
                    HashMap mm = (HashMap) (emap.getValue());
                    addNode(factory, som, mm);
                } //
                else
                {
                    // 分割值 数据格式 = 节点值_属性名称:属性值
                    //value = emap.getValue().toString().split("_");
                    //System.out.println(emap.getValue().toString());
                    som.addChild(factory.createOMText( emap.getValue().toString()));
                    /*if (value.length > 1) // 判断Attributer是否为空
                    {
                        // 给父节点加上Attribute属性
                        om.addAttribute(factory.createOMAttribute(value[1]
                                .split(":")[0], null, value[1].split(":")[1]));
                    }// if (value.length > 1)*/
                }

            }
            om.addChild(som);
        }
        return om;
    }

    public static void main(String[] args) throws Exception
    {
        /*
         * Vector v = new Vector(); HashMap map = null; for(int i=0;i<1 ;i++) {
         * 
         * map = new HashMap(); map.put("areacode", "1.1"); map.put("areaname",
         * "郑州市"); map.put("allname", "河南省郑州市"); map.put("level", "2");
         * map.put("parentcode", "1"); Vector vv = new Vector(); HashMap m =
         * null; for(int j =0 ;j <1 ;j++) { m = new HashMap(); m.put("unitname",
         * "单位"+j); vv.add(m); } map.put("unit", vv); v.add(map); } HashMap
         * pageinfo = new HashMap(); pageinfo.put("currentpage", "1");
         * pageinfo.put("totalpage", "5"); pageinfo.put("perpagenum", "20");
         * pageinfo.put("totalnum", "100");
         * 
         * HashMap mm = new HashMap(); mm.put("command1", "value1");
         * mm.put("pageinfo", pageinfo); mm.put("area", v); AxiomManager am =
         * new AxiomManager(); am.constuctXML(mm);
         */
        //String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><vcom><command>1.3.6.1.4.1.28185.1.6.2.26</command><operatetype>C</operatetype><packages><package><operate>A</operate><channel_code>1</channel_code><package_code>1</package_code><package_name>1</package_name><movies><movie><movie_code>11</movie_code><movie_name>11</movie_name><movie_type>11</movie_type></movie></movies></package><package><operate>B</operate><channel_code>2</channel_code><package_code>2</package_code><package_name>2</package_name><movies><movie><movie_code>22</movie_code><movie_name>22</movie_name><movie_type>22</movie_type></movie></movies></package></packages></vcom>";
        String xml=" <movie><filename>[韩国群星&MVLive合辑一].Jewelry.-.[I.Really.Like.You].LIVE.[SBS.2003.06.29].264avi</filename>"
                +"<title>111</title><createtime>1971-01-01 00:00:00</createtime></movie>";
        xml = xml.replaceAll("\\&", "&amp;");
       
        xml = xml.replaceAll("\\<", "&lt;");
        //System.out.println(xml);
        AxiomManager am = new AxiomManager();
        Vector v = am.getNodeList(xml, "//movie");
        //System.out.println(v + "\n");
        // Vector vv = am.getNodeList(xml, "//movie");
        // System.out.println(v+"\n");
    }

}
