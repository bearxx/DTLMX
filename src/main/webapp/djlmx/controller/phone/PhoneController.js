Ext.define('djlmx.controller.phone.PhoneController', {
  extend : 'Ext.app.Controller',
  views : [ 'phone.PhoneView'],
  idWorking : false,
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
	  var me = this;
	  Ext.Ajax.request({
		  url : 'rest/esper/startPhone',
		  success : function(respText) {
			  var res = Ext.JSON.decode(respText.responseText).success;
			  if(res != null) {
				  Ext.Msg.alert('tip', 'success');
				  btn.disable();
				  btn.up('toolbar').down('button[iconCls=tab-remove-icon]').enable();
				  btn.up('phoneView').down('panel[itemId=input_panel]')
				  		.down('button').enable();
				  me.isWorking = true;
				  me.waitForRes(btn);
			  } else {
				  Ext.Msg.alert('tip', 'internal error');
			  }
		  }
	  });
  },
  
  waitForRes : function(btn) {
	  var me = this;
	  Ext.Ajax.request({
		  url : 'rest/esper/result',
		  method : 'GET',
		  timeout : 60000,
		  success : function(resp) {
			  if(me.isWorking) {	
				  var result = Ext.decode(resp.responseText).data[0];
				  var resField = btn.up('phoneView').down('panel[itemId=result_panel]').down('textfield');
				  var resString = 'User: ' + result.user + '{id:[' + result.id + ']} '
				  		+ 'paid ' + result.newPayment
				  		+ ', average paid last 3 time is: ' + result.avgPayment;
				  resField.setValue(resString);
				  me.waitForRes(btn);
			  }
		  },
		  failure : function() {
			  me.waitForRes(btn);
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
	  var me = this;
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
			  		.down('button').disable();
				  me.isWorking = false;
			  } else {
				  Ext.Msg.alert('tip', 'internal error');
			  }
		  }
	  });
	  
  },
 
  onTreeRender : function (view) {
	  var status = null;
	  Ext.Ajax.request({
		  url : 'rest/esper/status',
		  method: 'GET',
		  success : function(resp) {
			  var isRunning = Ext.decode(resp.responseText).isRunning;
			  if(isRunning == true) {
				  view.down('button[iconCls=tab-add-icon]').disable();
				  view.down('button[iconCls=tab-remove-icon]').enable();
				  view.down('panel[itemId=input_panel]').down('button').enable();
			  }
		  }
	  });
  },
});
