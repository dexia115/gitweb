<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		});
	} catch (e) {
	}
</script>
<script src="js/highcharts/highcharts.src.js"></script>
<script src="js/highcharts/modules/exporting.js"></script>
<script src="js/equipment/report.js"></script>
		<style type="text/css">
${demo.css}
		</style>
		<script type="text/javascript">
$(function () {
	var categories = ['Jan','Feb','Mar','Apr'];
	var series = [{
        name: 'Tokyo',
        data: [49.9, 71.5, 106.4, 129.2]

    }, {
        name: 'New York',
        data: [83.6, 78.8, 98.5, 93.4]
    }];
	var data = [{
        name: 'Microsoft Internet Explorer',
        y: 56.33
    }, {
        name: 'Chrome',
        y: 24.03,
        sliced: true,
        selected: true
    }, {
        name: 'Firefox',
        y: 10.38
    }, {
        name: 'Safari',
        y: 4.77
    }, {
        name: 'Opera',
        y: 0.91
    }, {
        name: 'Proprietary or Undetectable',
        y: 0.2
    }];
	createColumnChart($('#container'),categories,series,function(){
		createPieChart($('#container'),data);
	});
	//createLineChart($('#container'),categories,series);
	
	//createPieChart($('#container'),data);
});
		</script>


<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
