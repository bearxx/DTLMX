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
    bbar: [
           { xtype: 'button', text: 'Button 1' }
    ],
    tbar: [
        { xtype: 'button', text: 'Button 1' }
    ]
});