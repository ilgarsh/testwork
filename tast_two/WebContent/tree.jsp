<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="directory" type="store.Directory"--%>
<c:forEach  var="directory" items="${directory.childs}">
    <div class="directory" id="${directory.id}">
        <div class="directory_icon"></div>
        <div class="directory_name">${directory.name}</div>
        <c:if test="${directory.showChild == true}" >
            <c:set var="directory" value="${directory}" scope="request"/>
            <jsp:include page="tree.jsp"/>
        </c:if>
    </div>
</c:forEach>
<script>

</script>