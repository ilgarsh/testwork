<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://einnovates.com/tree4jsp" prefix="t" %>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>Insert title here</title>

<style type="text/css">
img.leafImg {
  vertical-align: middle;
  padding-right: 5px;
  border: none;
}
</style>

</head>

<body>

<b>Example 1: Basic Tree:</b>
<br/><br/>
<t:tree treeData="${treeData}" imageBase="images/tree/"
  minusImage="16m.png" plusImage="16p.png"
  nodeObject="nodeObject">
</t:tree>



<br/><br/><br/><br/>

<b>Example 2: Tree with node icons:</b>
<br/><br/>
<t:tree id="t2" treeData="${treeData}" imageBase="images/tree/"
  minusImage="16mAndF.png" plusImage="16pAndF.png"
  nodeObject="nodeObject">
  <t:renderer nodeType="ALL_LEAFS"><img class="leafImg" src="images/tree/doc.png"/>${nodeObject}</t:renderer>
</t:tree>



<br/><br/><br/><br/>

<b>Example 3:</b> Tree (with a different look), linked to an iframe:
<br/><br/>

<div style="overflow: auto; float: left; width: 20%; height: 150px; margin-right: 10px;">
  <t:tree id="t3" treeData="${treeData}" imageBase="images/tree/"
    minusImage="16ma.png" plusImage="16pa.png"
    nodeObject="nodeObject">
    <t:renderer nodeType="ALL_LEAFS"><a href="nodeProperties.htm?nodeName=${nodeObject}" target="propertiesFrame"><img class="leafImg" src="images/tree/doc.png"/>${nodeObject} (Leaf Node)</a></t:renderer>
    <t:renderer nodeType="ALL"><a href="nodeProperties.htm?nodeName=${nodeObject}" target="propertiesFrame">${nodeObject}</a></t:renderer>
    <t:renderer nodeType="type1"><img class="leafImg" src="images/tree/doc.png"/><span style="color: red">${nodeObject} (Red Node)</span></t:renderer>
  </t:tree>

  <br/>
</div>
<div style="float: left;">
  <iframe id="propertiesFrame" name="propertiesFrame" width="400px" height="150px">
  </iframe>
</div>

</body>
</html>