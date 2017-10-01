<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach  var="directory" items="${directories.}">
    <div class="directory" id="${directory.id}">
        <div class="directory_icon"></div>
        <div class="directory_name">${directory.name}</div>
        <c:set var="directory" value=""
    </div>
</c:forEach>
