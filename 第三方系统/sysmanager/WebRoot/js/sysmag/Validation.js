function checkValidInput(value){
	validcharactor = /^(\w|[\u4E00-\u9FA5])*$/;
    return validcharactor.test(value);
}
