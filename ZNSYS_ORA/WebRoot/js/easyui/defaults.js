if ($.fn.pagination){
	$.fn.pagination.defaults.beforePageText = '第';
	$.fn.pagination.defaults.afterPageText = '共{pages}页';
	$.fn.pagination.defaults.displayMsg = '显示{from}到{to},共{total}记录';
}
if ($.fn.datagrid){
	$.fn.datagrid.defaults.loadMsg = '正在加载数据，请稍候...';
}
if ($.messager){
	$.messager.defaults.ok = '确定';
	$.messager.defaults.cancel = '取消';
}
if ($.fn.validatebox){
	$.fn.validatebox.defaults.missingMessage = '该输入项为必输项';
	$.fn.validatebox.defaults.rules.email.message = '请输入有效的电子邮件地址';
	$.fn.validatebox.defaults.rules.url.message = '请输入有效的URL地址';
	$.fn.validatebox.defaults.rules.length.message = '输入内容长度必须介于{0}和{1}之间';
}
if ($.fn.numberbox){
	$.fn.numberbox.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.combobox){
	$.fn.combobox.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.combotree){
	$.fn.combotree.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.calendar){
	$.fn.calendar.defaults.weeks = ['日','一','二','三','四','五','六'];
	$.fn.calendar.defaults.months = ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'];
}
if ($.fn.datebox){
	$.fn.datebox.defaults.currentText = '今天';
	$.fn.datebox.defaults.closeText = '关闭';
	$.fn.datebox.defaults.okText = '确定';
	$.fn.datebox.defaults.missingMessage = '该输入项为必输项';
	$.fn.datebox.defaults.formatter = function(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	};
	$.fn.datebox.defaults.parser = function(s){
		var ss = s.split('-');
		var y = parseInt(ss[0],10);
		var m = parseInt(ss[1],10);
		var d = parseInt(ss[2],10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
			return new Date(y,m-1,d);
		} else {
			return new Date();
		}
	};
}

if ($.fn.datetimebox && $.fn.datebox){
	$.extend($.fn.datetimebox.defaults,{
		currentText: $.fn.datebox.defaults.currentText,
		closeText: $.fn.datebox.defaults.closeText,
		okText: $.fn.datebox.defaults.okText,
		missingMessage: $.fn.datebox.defaults.missingMessage
	});
}

if($.fn.datagrid){
	$.fn.datagrid.defaults.onRowContextMenu=function(e,rowIndex,rowData){
		 e.preventDefault();
	};
	$.fn.datagrid.defaults.view.renderRow=function(target, fields, frozen, rowIndex, rowData){
		var opts = $.data(target, "datagrid").options;   
        //用于拼接字符串的数组   
        var cc = [];   
        if(frozen && opts.rownumbers) {   
            //rowIndex从0开始，而行号显示的时候是从1开始，所以这里要加1.   
            var rowNumber = rowIndex + 1;   
            //如果分页的话，根据页码和每页记录数重新设置行号   
            if(opts.pagination) {   
               rowNumber += (opts.pageNumber - 1) * opts.pageSize;   
           }   
           /**  
            * 先拼接行号列  
            * 注意DOM特征，用zenCoding可表达为"td.datagrid-td-rownumber>div.datagrid-cell-rownumber"  
            */  
            cc.push("<td class=\"datagrid-td-rownumber\"><div class=\"datagrid-cell-rownumber\">" + rowNumber + "</div></td>");   
        }   
        for(var i = 0; i < fields.length; i++) {   
            var field = fields[i];   
            var col = $(target).datagrid("getColumnOption", field);   
            if(col) {   
				//根据field是否为单层或者多层属性来解析
				var fieldnames=field.split('.');
				var value = rowData[fieldnames[0]];
				for(var j=1;j<fieldnames.length;j++) 
					if (value!=null) value=value[fieldnames[j]];
					else break;
                //获取用户定义的单元格样式，入参包括：单元格值，当前行数据，当前行索引(从0开始)   
                var style = col.styler ? (col.styler(value, rowData, rowIndex) || "") : "";   
                //如果是隐藏列直接设置display为none，否则设置为用户想要的样式   
                var styler = col.hidden ? "style=\"display:none;" + style + "\"" : (style ? "style=\"" + style + "\"" : "");   
                cc.push("<td field=\"" + field + "\" " + styler + ">");   
                //如果当前列是datagrid组件保留的ck列时，则忽略掉用户定义的样式，即styler属性对datagrid自带的ck列是不起作用的。   
                if(col.checkbox) {   
                    var styler = "";   
                } else {   
                	var styler = "";  
                    //设置文字对齐属性   
                    if(col.align) {   
                        styler += "text-align:" + col.align + ";";   
                    }   
                    //设置文字超出td宽时是否自动换行(设置为自动换行的话会撑高单元格)   
                    if(!opts.nowrap) {   
                        styler += "white-space:normal;height:auto;";   
                    } else {   
                        /**  
                         * 并不是nowrap属性为true单元格就肯定不会被撑高，这还得看autoRowHeight属性的脸色  
                         * 当autoRowHeight属性为true的时候单元格的高度是根据单元格内容而定的，这种情况主要是用于表格里展示图片等媒体。  
                         */  
                        if(opts.autoRowHeight) {   
                            styler += "height:auto;";   
                        }   
                    }   
                }   
                //这个地方要特别注意，前面所拼接的styler属性并不是作用于td标签上，而是作用于td下的div标签上。   
                cc.push("<div style=\"" + styler + "\" ");   
                //如果是ck列，增加"datagrid-cell-check"样式类   
                if(col.checkbox) {   
                    cc.push("class=\"datagrid-cell-check ");   
                }   
                //如果是普通列，增加"datagrid-cell-check"样式类   
                else {   
                    cc.push("class=\"datagrid-cell " + col.cellClass);   
                }   
                cc.push("\">");   
                /**  
                 * ck列光设置class是不够的，当突然还得append一个input进去才是真正的checkbox。此处未设置input的id，只设置了name属性。  
                 */  
                if(col.checkbox) {   
                    cc.push("<input type=\"checkbox\" name=\"" + field + "\" value=\"" + (value != undefined ? value : "") + "\"/>");   
                }   
                //普通列   
                else {   
                    /**  
                     * 如果单元格有formatter，则将formatter后生成的DOM放到td>div里面  
                     */  
                    if(col.formatter) {   
                        cc.push(col.formatter(value, rowData, rowIndex));   
                    }   
                    else {   
                        cc.push(value);   
                    }   
                }   
                cc.push("</div>");   
                cc.push("</td>");   
            }   
        }   
        //返回单元格字符串，注意这个函数内部并未把字符串放到文档流中。   
        return cc.join("");
	};
}