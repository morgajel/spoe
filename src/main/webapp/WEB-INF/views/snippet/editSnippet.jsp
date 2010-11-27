<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<html>
        <%@ include file="/WEB-INF/jsp/header.jsp"%>

<script type="text/javascript" src="/js/jquery.wymeditor.min.js"></script>
<script type="text/javascript" src="/js/jquery.ui.js"></script>
<script type="text/javascript" src="/js/jquery.ui.resizable.js"></script>
<script type="text/javascript" src="/js/plugins/hovertools/jquery.wymeditor.hovertools.js"></script>
<script type="text/javascript" src="/js/plugins/resizable/jquery.wymeditor.resizable.js"></script>


<script type="text/javascript">

jQuery(function() {
    jQuery('.wymeditor').wymeditor({
    
        html: '', //set editor's value
        skin: 'compact',                //activate silver skin
        toolsItems: [
                     {'name': 'Bold', 'title': 'Strong', 'css': 'wym_tools_strong'}, 
                     {'name': 'Italic', 'title': 'Emphasis', 'css': 'wym_tools_emphasis'},
                     {'name': 'InsertOrderedList', 'title': 'Ordered_List',
                         'css': 'wym_tools_ordered_list'},
                     {'name': 'InsertUnorderedList', 'title': 'Unordered_List',
                         'css': 'wym_tools_unordered_list'},
                     {'name': 'Paste', 'title': 'Paste_From_Word',
                         'css': 'wym_tools_paste'},
                     {'name': 'Undo', 'title': 'Undo', 'css': 'wym_tools_undo'},
                     {'name': 'Redo', 'title': 'Redo', 'css': 'wym_tools_redo'}
                 ],        
        postInit: function(wym) {

            wym.hovertools();          //activate hovertools
            wym.resizable({
                handles:"s" 
            });           //and resizable plugins
            
        }
        
    });
});

</script>

<h1>Create a Snippet</h1>
<div class="msg">${message}</div>
<form:form modelAttribute="editSnippetForm" action="/snippet/save" method="post">
    <form:label for="title" path="title" cssErrorClass="error">Title:</form:label>
    <form:input path="title" cssClass='wym_title' size='40'  />
    <form:errors path="title" />

    <form:hidden path="snippetId" />
        <form:errors path="snippetId" />


    <form:textarea path="content" cssClass="wymeditor"/>
        <form:errors path="content" />
    <form:label for="published" path="published"cssErrorClass="error">Publish?</form:label>
    <form:checkbox path="published"/><br/>
    <input type="submit" class="wymupdate" value="save" />
</form:form>



    <%@ include file="/WEB-INF/jsp/footer.jsp"%>
    </body>
</html>

