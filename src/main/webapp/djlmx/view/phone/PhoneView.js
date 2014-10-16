Ext.define('djlmx.view.phone.PhoneView',{
	extend : 'Ext.panel.Panel',
	alias : 'widget.phoneView',
	title: 'Phone',
    layout: {
        type: 'fit',
        align: 'stretch',
        padding: 5
    },
    items: [{
        xtype: 'datefield',
        fieldLabel: 'Start date'
    }, {
        xtype: 'datefield',
        fieldLabel: 'End date'
    }],
    bbar: {},
    tbar: {
    	xtype : 'toolbar',
		cls : 'x-panel-header',
		layout : 'column',
		items : [  {
            text : '添加',
            xtype : 'button',
            iconCls : 'tab-add-icon',
            tooltip : '添加'
        }, '-', {
            text : '删除',
            xtype : 'button',
            iconCls : 'tab-remove-icon',
            tooltip : '删除'
        },  '-', {
			labelAlign : 'right',
			fieldLabel : '输入查询code',
			itemId : 'code',
			text : 'codetext',
			xtype : 'textfield',
			tooltip : 'code'
		},'-', {
			fieldLabel : '填入查询内容',
			labelAlign : 'right',
			itemId : 'name',
			text : 'nametext',
			xtype : 'textfield',
			tooltip : 'name'
		}, '-', {
			xtype : 'button',
			iconCls : 'search-icon',
			tooltip : 'search',
			action : 'case-search',

		} ]
	
    }
});