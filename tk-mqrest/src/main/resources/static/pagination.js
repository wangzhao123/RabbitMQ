var app = angular.module("myApp", []);
app.controller("paginationController", function ($scope, $http,$location) {
	$http.get("/mqrest/findAll").then(function (response) {
		
//		var json = JSON.stringify(response.data); 
//		alert(json);
		
		//数据源
		$scope.data = response.data.datas;
		//分页总数
		$scope.pageSize = 5;
		$scope.pages = Math.ceil($scope.data.length / $scope.pageSize); //分页数
		$scope.newPages = $scope.pages > 5 ? 5 : $scope.pages;
		$scope.pageList = [];
		$scope.selPage = 1;
		
		$scope.pre_btn="disabled";
		
		//设置表格数据源(分页)
		$scope.setData = function () {
			$scope.connectInfors = $scope.data.slice(($scope.pageSize * ($scope.selPage - 1)), ($scope.selPage * $scope.pageSize));//通过当前页数筛选出表格当前显示数据
		}
		$scope.connectInfors = $scope.data.slice(0, $scope.pageSize);
		//分页要repeat的数组
		for (var i = 0; i < $scope.newPages; i++) {
			$scope.pageList.push(i + 1);
		}
		//打印当前选中页索引
		$scope.selectPage = function (page) {
			
			if(page==1){
				$scope.pre_btn="disabled";
			}else{
				$scope.pre_btn="";
			}
			
			if(page == $scope.pages){
				$scope.next_btn="disabled";
			}else{
				$scope.next_btn="";
			}
			
			//不能小于1大于最大
			if (page < 1 || page > $scope.pages) return;
			//最多显示分页数5
			if (page > 2) {
				//因为只显示5个页数，大于2页开始分页转换
				var newpageList = [];
				for (var i = (page - 3) ; i < ((page + 2) > $scope.pages ? $scope.pages : (page + 2)) ; i++) {
					newpageList.push(i + 1);
				}
				$scope.pageList = newpageList;
			}
			$scope.selPage = page;
			$scope.setData();
			$scope.isActivePage(page);
			console.log("选择的页：" + page);
		};
		//设置当前选中页样式
		$scope.isActivePage = function (page) {
			return $scope.selPage == page;
		};
		//上一页
		$scope.Previous = function () {
			$scope.selectPage($scope.selPage - 1);
		};
		//下一页
		$scope.Next = function () {
			$scope.selectPage($scope.selPage + 1);
		};
	});
	
	$scope.startListener=function(listenerId){
		window.location.href="/mqrest/start?id="+id;
	};
})
