Ext.define('djlmx.controller.phone.PhoneController', {
  extend : 'Ext.app.Controller',
  views : [ 'phone.PhoneView'],
  init : function() {
    this.control({
        'phoneView': {
            render: this.onTreeRender
        }
    });
  },
 

  onTreeRender: function (view) {
  },
});
