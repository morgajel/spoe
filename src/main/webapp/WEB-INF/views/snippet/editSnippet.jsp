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
<form method="post" action="">
<label>Title:</label>
<input type='text' class='wym_title' name='title' size='40' value="${title}" />

<textarea class="wymeditor">${content}</textarea>

<input type="submit" class="wymupdate" value="save" />
</form>



    <%@ include file="/WEB-INF/jsp/footer.jsp"%>
    </body>
</html>

