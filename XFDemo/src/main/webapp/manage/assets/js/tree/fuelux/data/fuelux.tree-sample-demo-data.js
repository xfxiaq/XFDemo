var DataSourceTree = function(options) {
	this._data 	= options.data;
	this._delay = options.delay;
}

DataSourceTree.prototype.data = function(options, callback) {
	var self = this;
	var $data = null;

	if(!("name" in options) && !("type" in options)){
		$data = this._data;//the root tree
		callback({ data: $data });
		return;
	}
	else if("type" in options && options.type == "folder") {
		if("additionalParameters" in options && "children" in options.additionalParameters)
			$data = options.additionalParameters.children;
		else $data = {}//no data
	}
	
	if($data != null)//this setTimeout is only for mimicking some random delay
		setTimeout(function(){callback({ data: $data });} , parseInt(Math.random() * 500) + 200);

	//we have used static data here
	//but you can retrieve your data dynamically from a server using ajax call
	//checkout examples/treeview.html and examples/treeview.js for more info
};

//	var tree_data = {};
//	var data = ['a','b','c','d'];
//	 
//	var childrens = [];
//	
//	var children1 = {'parents':'a','plantNo':'辽A1234','id':'01'};
//	var children2 = {'parents':'b','plantNo':'辽B1234','id':'02'};
//	var children3 = {'parents':'c','plantNo':'辽C1234','id':'03'};
//	var children4 = {'parents':'d','plantNo':'辽D1234','id':'04'};
//	var children5 = {'parents':'a','plantNo':'辽B1235','id':'02'};
//	
//	childrens.push(children1);
//	childrens.push(children2);
//	childrens.push(children3);
//	childrens.push(children4);
//	childrens.push(children5);
//	
//	for(var y=0;y<data.length;y++){
//		var userChildren = [];
//		tree_data[data[y]] = {name: data[y], type: 'folder'};
//		for(var i=0;i<childrens.length;i++){
//			if(data[y] == childrens[i].parents){
//				userChildren.push({name: childrens[i].plantNo, id: childrens[i].id, type: "item"});
//			}
//		}
//		tree_data[data[y]]['additionalParameters'] = {children: userChildren};
//	}
//	console.log("tree_data:",tree_data);
//	var treeDataSource = new DataSourceTree({data: tree_data});
//	console.log("treeDataSource:",treeDataSource);


//var tree_data = {
//	'for-sale' : {name: 'For Sale', type: 'folder'}	,
//	'vehicles' : {name: 'Vehicles', type: 'folder'}	, 
//	'pets' : {name: 'Pets', type: 'folder'}	,
//	'tickets' : {name: 'Tickets', type: 'item'}	, 
//	'personals' : {name: 'Personals', type: 'item'}
//}
//tree_data['for-sale']['additionalParameters'] = {
//	'children' : {
//		'appliances' : {name: 'Appliances', type: 'item'},
//		'arts-crafts' : {name: 'Arts & Crafts', type: 'item'},
//		'clothing' : {name: 'Clothing', type: 'item'},
//		'computers' : {name: 'Computers', type: 'item'} 
//	}
//}
//tree_data['vehicles']['additionalParameters'] = {
//	'children' : { 
//		'motorcycles' : {name: 'Motorcycles', type: 'item'},
//		'boats' : {name: 'Boats', type: 'item'}
//	}
//}
// 
// 
//tree_data['pets']['additionalParameters'] = {
//	'children' : {
//		'cats' : {name: 'Cats', type: 'item'},
//		'dogs' : {name: 'Dogs', type: 'item'},
//		'horses' : {name: 'Horses', type: 'item'},
//		'reptiles' : {name: 'Reptiles', type: 'item'}
//	}
//}
//console.log("tree_Data:",tree_data);
//var treeDataSource = new DataSourceTree({data: tree_data});
//console.log("treeDataSource:",treeDataSource);