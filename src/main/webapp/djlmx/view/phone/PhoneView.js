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
            text : '初始化Phone服务',
            xtype : 'button',
            iconCls : 'tab-add-icon',
            tooltip : '初始化所需资源'
        }, '-', {
            text : '终止Phone服务',
            xtype : 'button',
            iconCls : 'tab-remove-icon',
            tooltip : '终止Phone服务，并释放相关资源'
        }]
	
    }
});