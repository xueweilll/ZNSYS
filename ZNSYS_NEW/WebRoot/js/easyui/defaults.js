if ($.fn.pagination){
	$.fn.pagination.defaults.beforePageText = '��';
	$.fn.pagination.defaults.afterPageText = '��{pages}ҳ';
	$.fn.pagination.defaults.displayMsg = '��ʾ{from}��{to},��{total}��¼';
}
if ($.fn.datagrid){
	$.fn.datagrid.defaults.loadMsg = '���ڼ������ݣ����Ժ�...';
}
if ($.messager){
	$.messager.defaults.ok = 'ȷ��';
	$.messager.defaults.cancel = 'ȡ��';
}
if ($.fn.validatebox){
	$.fn.validatebox.defaults.missingMessage = '��������Ϊ������';
	$.fn.validatebox.defaults.rules.email.message = '��������Ч�ĵ����ʼ���ַ';
	$.fn.validatebox.defaults.rules.url.message = '��������Ч��URL��ַ';
	$.fn.validatebox.defaults.rules.length.message = '�������ݳ��ȱ������{0}��{1}֮��';
}
if ($.fn.numberbox){
	$.fn.numberbox.defaults.missingMessage = '��������Ϊ������';
}
if ($.fn.combobox){
	$.fn.combobox.defaults.missingMessage = '��������Ϊ������';
}
if ($.fn.combotree){
	$.fn.combotree.defaults.missingMessage = '��������Ϊ������';
}
if ($.fn.calendar){
	$.fn.calendar.defaults.weeks = ['��','һ','��','��','��','��','��'];
	$.fn.calendar.defaults.months = ['һ��','����','����','����','����','����','����','����','����','ʮ��','ʮһ��','ʮ����'];
}
if ($.fn.datebox){
	$.fn.datebox.defaults.currentText = '����';
	$.fn.datebox.defaults.closeText = '�ر�';
	$.fn.datebox.defaults.okText = 'ȷ��';
	$.fn.datebox.defaults.missingMessage = '��������Ϊ������';
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
        //����ƴ���ַ���������   
        var cc = [];   
        if(frozen && opts.rownumbers) {   
            //rowIndex��0��ʼ�����к���ʾ��ʱ���Ǵ�1��ʼ����������Ҫ��1.   
            var rowNumber = rowIndex + 1;   
            //�����ҳ�Ļ�������ҳ���ÿҳ��¼�����������к�   
            if(opts.pagination) {   
               rowNumber += (opts.pageNumber - 1) * opts.pageSize;   
           }   
           /**  
            * ��ƴ���к���  
            * ע��DOM��������zenCoding�ɱ��Ϊ"td.datagrid-td-rownumber>div.datagrid-cell-rownumber"  
            */  
            cc.push("<td class=\"datagrid-td-rownumber\"><div class=\"datagrid-cell-rownumber\">" + rowNumber + "</div></td>");   
        }   
        for(var i = 0; i < fields.length; i++) {   
            var field = fields[i];   
            var col = $(target).datagrid("getColumnOption", field);   
            if(col) {   
				//����field�Ƿ�Ϊ������߶������������
				var fieldnames=field.split('.');
				var value = rowData[fieldnames[0]];
				for(var j=1;j<fieldnames.length;j++) 
					if (value!=null) value=value[fieldnames[j]];
					else break;
                //��ȡ�û�����ĵ�Ԫ����ʽ����ΰ�������Ԫ��ֵ����ǰ�����ݣ���ǰ������(��0��ʼ)   
                var style = col.styler ? (col.styler(value, rowData, rowIndex) || "") : "";   
                //�����������ֱ������displayΪnone����������Ϊ�û���Ҫ����ʽ   
                var styler = col.hidden ? "style=\"display:none;" + style + "\"" : (style ? "style=\"" + style + "\"" : "");   
                cc.push("<td field=\"" + field + "\" " + styler + ">");   
                //�����ǰ����datagrid���������ck��ʱ������Ե��û��������ʽ����styler���Զ�datagrid�Դ���ck���ǲ������õġ�   
                if(col.checkbox) {   
                    var styler = "";   
                } else {   
                	var styler = "";  
                    //�������ֶ�������   
                    if(col.align) {   
                        styler += "text-align:" + col.align + ";";   
                    }   
                    //�������ֳ���td��ʱ�Ƿ��Զ�����(����Ϊ�Զ����еĻ���Ÿߵ�Ԫ��)   
                    if(!opts.nowrap) {   
                        styler += "white-space:normal;height:auto;";   
                    } else {   
                        /**  
                         * ������nowrap����Ϊtrue��Ԫ��Ϳ϶����ᱻ�Ÿߣ��⻹�ÿ�autoRowHeight���Ե���ɫ  
                         * ��autoRowHeight����Ϊtrue��ʱ��Ԫ��ĸ߶��Ǹ��ݵ�Ԫ�����ݶ����ģ����������Ҫ�����ڱ����չʾͼƬ��ý�塣  
                         */  
                        if(opts.autoRowHeight) {   
                            styler += "height:auto;";   
                        }   
                    }   
                }   
                //����ط�Ҫ�ر�ע�⣬ǰ����ƴ�ӵ�styler���Բ�����������td��ǩ�ϣ�����������td�µ�div��ǩ�ϡ�   
                cc.push("<div style=\"" + styler + "\" ");   
                //�����ck�У�����"datagrid-cell-check"��ʽ��   
                if(col.checkbox) {   
                    cc.push("class=\"datagrid-cell-check ");   
                }   
                //�������ͨ�У�����"datagrid-cell-check"��ʽ��   
                else {   
                    cc.push("class=\"datagrid-cell " + col.cellClass);   
                }   
                cc.push("\">");   
                /**  
                 * ck�й�����class�ǲ����ģ���ͻȻ����appendһ��input��ȥ����������checkbox���˴�δ����input��id��ֻ������name���ԡ�  
                 */  
                if(col.checkbox) {   
                    cc.push("<input type=\"checkbox\" name=\"" + field + "\" value=\"" + (value != undefined ? value : "") + "\"/>");   
                }   
                //��ͨ��   
                else {   
                    /**  
                     * �����Ԫ����formatter����formatter�����ɵ�DOM�ŵ�td>div����  
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
        //���ص�Ԫ���ַ�����ע����������ڲ���δ���ַ����ŵ��ĵ����С�   
        return cc.join("");
	};
}