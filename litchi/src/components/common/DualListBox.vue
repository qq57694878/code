<template>

  <div class="hr hr-16 hr-dotted"></div>

  <div class="form-group">

    <label class="col-sm-2 control-label" v-bind:for="name">{{{description}}}</label>
    <div class="col-sm-10">


      <!-- #section:plugins/input.duallist -->
      <select multiple="multiple" size="10" v-bind:name="name" v-bind:id="name">
        <span v-text="items | colorCls"></span>

      </select>
    </div>
    <span>Selected: {{ model }}</span>
    <span>items: {{ items }}</span>
  </div>
  <div class="hr hr-16 hr-dotted"></div>

</template>

<script>

  import {reqDuailListboxData} from '../../vuex/modules/common/DuailListBoxActions';

  import {getInitBox} from '../../vuex/modules/common/DuailListBoxGetters'

  export default {
    props: ["name", "model", "url", "props", "description", "items"],

    vuex: {
      actions: {
        reqDuailListboxData
      }


    },
//    watch: {
//      'model': function (val, oldVal) {
//        var self = this;
//        console.info("watch");
//        this.reqDuailListboxData(this, this.url, this.props, function (comp, items) {
//          self.items = items;
//          self.$nextTick(function () {
//            var listbox = $("#" + self.name).bootstrapDualListbox();
//            var container = listbox.bootstrapDualListbox('getContainer');
//            container.find('.btn').addClass('btn-white btn-info btn-bold');
//          });
//        });
//      }
//    },
    filters:{
      colorCls:function(values){
        for(var temp in values){
          console.debug(temp.code);
        }

      }
    },
    ready: function () {
      var self = this;
      console.info("updated");
      this.reqDuailListboxData(this, this.url, this.props, function (comp, items) {
        self.items = items;
        self.$nextTick(function () {
          var listbox = $("#" + self.name).bootstrapDualListbox();
          var container = listbox.bootstrapDualListbox('getContainer');
          container.find('.btn').addClass('btn-white btn-info btn-bold');
        });
      });
    }
  }
</script>
