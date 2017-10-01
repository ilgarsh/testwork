<!DOCTYPE html>
<html lang="en">
<style>
    body {
        padding: 50px;
    }
    .directory {
        margin: 10px 10px;
        min-width: 100px;
        clear: both;
    }
    .directory > .directory {
        margin-left: 30px;
    }
    .directory > .directory_icon {
        float: left;
        width: 30px;
        height: 30px;
        background-image: url("close_folder.png");
        background-size: cover;
    }
    .directory > .directory_name {
        padding: 5px;
        float: left;
        min-width: 70px;
        height: 30px;
    }
    .control {
        float: right;
        min-height: 300px;
        border-radius: 3px;
        border: solid grey;
        padding: 20px;
    }
    .add, .delete, .move {
        margin-top: 30px;
    }
    .control > .add > #directory_add_name {
        width: 166px;
    }
    .control > .add > #create_directory_path {
        width: 166px;
    }
    .control > .delete > #directory_delete_name {
        width: 166px;
    }
    .control > .move > #old_directory_path {
        width: 166px;
    }
    .control > .move > #new_directory_path {
        width: 166px;
    }

</style>
<head>
    <meta charset="UTF-8">
    <title>Task Two</title>
</head>
<body>
<%@include file="tree.jsp"%>
<div class="control">
    <form action="POST" class="add">
        <input id="directory_add_name"/>
        <div>create in..</div>
        <div id="create_directory_path">New directory path</div>
        <button id="directory_add">Add</button>
    </form>
    <form action="POST" class="delete">
        <div id="directory_delete_name">Directory name</div>
        <button id="directory_delete">Delete</button>
    </form>
    <form action="POST" class="move">
        <div id="old_directory_path">Old directory path</div>
        <div>move to...</div>
        <div id="new_directory_path">New directory path</div>
        <button id="directory_move">Move</button>
    </form>
</div>
</body>
</html>