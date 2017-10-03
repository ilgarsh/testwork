<!DOCTYPE html>
<html lang="en">
<head>
    <script src="jquery-3.2.1.min.js"></script>
    <script src="jQuery-contextMenu/dist/jquery.contextMenu.min.js"></script>
    <script src="jQuery-contextMenu/dist/jquery.ui.position.min.js"></script>
    <link rel="stylesheet" href="jQuery-contextMenu/dist/jquery.contextMenu.min.css"/>
    <link rel="stylesheet" type="text/css" href="style1.css">
    <meta charset="UTF-8">
    <title>Task Two</title>
    <script>
        var selectedDiv;
        $(function() {
            $.contextMenu({
                selector: '.directory',
                callback: function(key, options) {
                    var div_name;
                    switch (key) {
                        case "add":
                            div_name = prompt("Please enter directory name");
                            $.post("",
                                {
                                    command: key,
                                    div_id: options.$trigger.attr("id"),
                                    div_name: div_name
                                }, function (response) {
                                    var parent = options.$trigger[0].classList;
                                    if (parent.contains('open')) {
                                        createDirectory(document.getElementById(response.parent),
                                            response);
                                    }
                                });
                            break;
                        case "edit":
                            div_name = prompt("Please enter new directory name");
                            $.post("",
                                {
                                    command: key,
                                    div_id: options.$trigger.attr("id"),
                                    div_name: div_name,
                                    success: function () {
                                        changeDirectory(options.$trigger.attr("id"), div_name);
                                    }
                                });
                            break;
                        case "copy":
                            $.post("",
                                {
                                    command: key,
                                    div_id: options.$trigger.attr("id")
                                });
                            break;
                        case "paste":
                            $.post("",
                                {
                                    command: key,
                                    div_id: options.$trigger.attr("id")
                                }, function (response) {
                                    var parent_id = document.getElementById(options.$trigger.attr("id"));
                                    for (var i = 0; i < response.childs.length; i++) {
                                        if (!document.getElementById(response.childs[i].id)) {
                                            createDirectory(parent_id,
                                                response.childs[i]);
                                        }
                                    }
                                });
                            break;
                        case "delete":
                            $.post("",
                                {
                                    command: key,
                                    div_id: options.$trigger.attr("id"),
                                    success: function () {
                                        deleteDirectory(options.$trigger.attr("id"));
                                    }
                                }
                            )
                    }
                },
                items: {
                    "add": {name: "Add", icon: "add"},
                    "edit": {name: "Edit", icon: "edit"},
                    "copy": {name: "Copy", icon: "copy"},
                    "paste": {name: "Paste", icon: "paste"},
                    "delete": {name: "Delete", icon: "delete"}
                }
            });

            $(document).on('click', '.directory', function(e){
                $.post("", {
                    command: "show_child",
                    div_id: this.id,
                    success: function () {
                        var div = e.currentTarget;
                        div.classList.add('open');
                        if (!selectDiv(e)) {
                            openFolderAnimation(div);
                        }
                    }
                }, function (response) {
                    var parent = document.getElementById(response.id);
                    removeOpenFolderAnimation(parent);
                    for (var i = 0; i < response.childs.length; i++) {
                        if (!document.getElementById(response.childs[i].id)) {
                            createDirectory(parent,
                                response.childs[i]);
                        }
                    }
                });
            })
        });

        function openFolderAnimation(div) {
            var icon_div;
            console.log(div.childNodes);
            if (div.childNodes) {
                for (var i = 0; i < div.childNodes.length; i++) {
                    var child = div.childNodes;
                    if (child[i].classList && child[i].classList.contains('directory_icon')) {
                        icon_div = div.childNodes[i];
                        icon_div.classList.remove('directory_icon');
                        icon_div.classList.add('loader');
                        break;
                    }
                }
            }
        }

        function removeOpenFolderAnimation(div) {
            var icon_div;
            console.log(div.childNodes);
            if (div.childNodes) {
                for (var i = 0; i < div.childNodes.length; i++) {
                    var child = div.childNodes;
                    if (child[i].classList && child[i].classList.contains('loader')) {
                        icon_div = div.childNodes[i];
                        icon_div.classList.remove('loader');
                        icon_div.classList.add('directory_icon');
                        break;
                    }
                }
            }
        }

        function selectDiv(e) {
            var isLoaded = false;
            e.stopPropagation();
            if (selectedDiv && selectedDiv.classList.contains('selected')) {
                selectedDiv.classList.remove('selected');
            }
            selectedDiv = e.currentTarget;
            selectedDiv.classList.add('selected');
            for (var i = 0; i < selectedDiv.childNodes.length; i++) {
                if (selectedDiv.childNodes[i].classList !== undefined) {
                    if (selectedDiv.childNodes[i].classList.contains('show')) {
                        isLoaded = true;
                        selectedDiv.childNodes[i].classList.remove('show');
                        selectedDiv.childNodes[i].classList.add('hide');
                    } else if (selectedDiv.childNodes[i].classList.contains('hide')) {
                        isLoaded = true;
                        selectedDiv.childNodes[i].classList.remove('hide');
                        selectedDiv.childNodes[i].classList.add('show');
                    }
                }
            }
            return isLoaded;
        }

        //---------CREATE, DELETE, MOVE AND CHANGE DIRECTORY-----------//
        function createDirectory(parent, directory) {
            var mainDiv = document.createElement("div");
            mainDiv.classList.add("directory");
            var sibling = $(parent).find(".directory")[0];
            var isHide;
            if (sibling !== undefined) {
                isHide = sibling.classList.contains('hide');
            }
            if (isHide) {
                mainDiv.classList.add("hide");
            } else {
                mainDiv.classList.add("show");
            }


            var nameDiv = document.createElement("div");
            nameDiv.classList.add("directory_name");
            nameDiv.innerText = directory.name;

            var iconDiv = document.createElement("div");
            iconDiv.classList.add("directory_icon");

            mainDiv.id = directory.id;

            mainDiv.appendChild(iconDiv);
            mainDiv.appendChild(nameDiv);
            parent.appendChild(mainDiv);
        }

        function deleteDirectory(id) {
            //send child to tomcat
            //tomcat deletes child and all child's child
            var element = document.getElementById(id);
            element.parentNode.removeChild(element);
        }

        function changeDirectory(id, directory) {
            $("#"+id ).find(".directory_name")[0].innerText = directory;
        }
        //------------------------------------------------------------//
    </script>

</head>
<body>
<div class="directory" id="0">
    <div class="directory_icon"></div>
    <div class="directory_name">Root</div>
</div>
</body>
</html>