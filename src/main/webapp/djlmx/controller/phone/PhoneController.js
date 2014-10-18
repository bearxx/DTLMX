Ext.define('djlmx.controller.phone.PhoneController', {
  extend : 'Ext.app.Controller',
  views : [ 'phone.PhoneView'],
  init : function() {
    this.control({
        'phoneView': {
            render: this.onTreeRender
        },
        'phoneView panel[itemId=input_panel] button' : {
        	click : this.sendPhone
        },
        'phoneView toolbar button[iconCls=tab-add-icon]' : {
        	click : this.initPhoneService
        },
        'phoneView toolbar button[iconCls=tab-remove-icon]' : {
        	click : this.stopPhoneService
        }
    });
  },
  
  initPhoneService : function(btn, e) {
	  Ext.Ajax.request({
		  url : 'rest/esper/startPhone',
		  success : function(respText) {
			  console.log(respText.responseText);
			  var res = Ext.JSON.decode(respText.responseText).success;
			  if(res != null) {
				  Ext.Msg.alert('tip', 'success');
				  btn.disable();
				  btn.up('toolbar').down('button[iconCls=tab-remove-icon]').enable();
				  btn.up('phoneView').down('panel[itemId=input_panel]')
				  		.down('button').enable();
			  } else {
				  Ext.Msg.alert('tip', 'internal error');
			  }
		  }
	  });
  },
  
  sendPhone : function(btn, e) {
	  var inputPanel = btn.up('panel[itemId=input_panel]'),
	  	  id = inputPanel.down('textfield[fieldLabel=Id]').getValue(),
	  	  user = inputPanel.down('textfield[fieldLabel=Username]').getValue(),
	  	  payment = inputPanel.down('textfield[fieldLabel=Payment]').getValue();
	  Ext.Ajax.request({
		  url : 'rest/esper/phone',
		  method : 'GET',
		  params : {
			  id : id,
			  user : user,
			  payment : payment
		  }
	  });
  },
  
  stopPhoneService : function(btn, e) {
	  Ext.Ajax.request({
		  url : 'rest/esper/stopPhone',
		  success : function(respText) {
			  var res = Ext.JSON.decode(respText.responseText).success;
			  console.log(res);
			  if(res == true) {
				  Ext.Msg.alert('tip', 'success');
				  btn.disable();
				  btn.up('toolbar').down('button[iconCls=tab-add-icon]').enable();
				  btn.up('phoneView').down('panel[itemId=input_panel]')
			  		.down('button').enable();
			  } else {
				  Ext.Msg.alert('tip', 'internal error');
			  }
		  }
	  });
	  
  },
 
  onTreeRender : function (view) {
	  
  },
});
