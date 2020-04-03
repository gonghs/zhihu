window.commUtils = {
  randomKey:function () {
      return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
  },
  getUUid:function () {
      return (this.randomKey()+this.randomKey()+"-"+this.randomKey()+"-"+this.randomKey()+"-"+this.randomKey()+"-"+this.randomKey()+this.randomKey()+this.randomKey());
  }  
};