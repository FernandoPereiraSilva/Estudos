var Dictionary = {
	ESPACO: "&nbsp;",
	COMBO_BOX: "Selecione...",
	Icon: {
		CRIAR: "criar.png",
		CONSULTAR: "consultar.png",
		ALTERAR: "alterar.png",
		DELETAR: "deletar.png",
		SEND: "send.png",
		DETALHE: "detalhe.png"
	},
	Service: {
		Cliente: {
			CADASTRAR: "http://localhost:8080/service/cliente/cadastrar",
			CONSULTAR: "http://localhost:8080/service/cliente/consultar",
			CONSULTAR_TODOS: "http://localhost:8080/service/cliente/consultar/todos",
			ALTERAR: "http://localhost:8080/service/cliente/alterar",
			DELETAR: "http://localhost:8080/service/cliente/deletar"
		},
		Produto: {
			CADASTRAR: "http://localhost:8080/service/produto/cadastrar",
			CONSULTAR: "http://localhost:8080/service/produto/consultar",
			CONSULTAR_TODOS: "http://localhost:8080/service/produto/consultar/todos",
			ALTERAR: "http://localhost:8080/service/produto/alterar",
			DELETAR: "http://localhost:8080/service/produto/deletar"
		},
		Pedido: {
			CADASTRAR: "http://localhost:8080/service/pedido/cadastrar",
			CONSULTAR: "http://localhost:8080/service/pedido/consultar",
			CONSULTAR_TODOS: "http://localhost:8080/service/pedido/consultar/todos",
			ALTERAR: "http://localhost:8080/service/pedido/alterar",
			DELETAR: "http://localhost:8080/service/pedido/deletar"
		},
		PedidoItem: {
			CADASTRAR: "http://localhost:8080/service/pedidoItem/cadastrar",
			CONSULTAR: "http://localhost:8080/service/pedidoItem/consultar",
			CONSULTAR_TODOS: "http://localhost:8080/service/pedidoItem/consultar/todos",
			ALTERAR: "http://localhost:8080/service/pedidoItem/alterar",
			DELETAR: "http://localhost:8080/service/pedidoItem/deletar"
		},
		PedidoStatus: {
			CONSULTAR: "http://localhost:8080/service/pedidoStatus/consultar"
		}
	},
	CssClass: {
		PEDIDO_STATUS: "PedidoStatus"
	},
	Model: {
		PedidoStatus: {
			CONCLUIDO: 2,
			CANCELADO: 3
		}
	}
}

//Carregar padrao
function loadDefault() {
	$("#defaultNav").load("defaultNav.html");
	
	jsonToFields(getParamsFromLink());
	setMasks();
}

function loadDefaultImports() {
	loadMeta();
	loadStyleFiles();
	loadScriptFiles();
}

function loadMeta() {
	$("<meta />").attr({"http-equiv": "Content-Type", "content": "text/html", "charset": "UTF-8"}).appendTo($("head"));
}

function loadStyleFiles() {
	var hrefs = [
		"webjars/bootstrap/4.3.1/css/bootstrap.min.css",
		"default.css"
	];

	$(hrefs).each(function(index, value){
		$("<link />").attr({"rel": "stylesheet", "type": "text/css", "href": value}).appendTo($("head"));
	});
}

function loadScriptFiles() {
	var srcs = [
		"webjars/bootstrap/4.3.1/js/bootstrap.min.js",
		"https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.js"
	];
	
	$(srcs).each(function(index, value){
		$("<script />").attr({"type": "text/javascript", "src": value}).appendTo($("head"));
	});
}

function getParamsFromLink() {
	var url = parent.document.URL; 
	if (url.indexOf("?") < 0) {
		url = document.URL;
	}
	
	if (url.indexOf("?") > 0) {
		var json = {};
		var array = url.substring(url.indexOf('?') + 1, url.length).split("&");
		
		$(array).each(function(index, item){
			json[item.substring(0, item.indexOf("="))] = item.substring(item.indexOf("=") + 1, item.length); 
		});
		
		return json;
	} else {
		return "";
	}
}

function jsonToFields(json) {
	if (json instanceof Array) {
		$(json).each(function(index, value){
			jsonToFields(value);
		});
	} else {
		$(".form-control").each(function(index, value){
			var id = $(value).attr("id");
			if (id.indexOf(".") >= 0) {
				var result = json;
				$(id.split(".")).each(function(index2, value2){
					if (result != undefined) {
						result = result[value2];
					}
				});
				
				if (result != undefined) {
					if ($(value).attr("type") == "date") {
						$(value).val(result.substring(0, 10));
					} else {
						$(value).val(result);
					}
				}
			} else {
				if (json[id] != undefined) {
					if ($(value).attr("type") == "date") {
						$(value).val(json[id].substring(0, 10));
					} else {
						$(value).val(json[id]);
					}
				}
			}
		});
	}
}

function jsonToFilter(json) {
	var filter = "";
	
	$(Object.keys(json)).each(function(index, value){
		filter += ((filter != "") ? "&" : "") + value + "=" + json[value];
	});
	
	return ((filter == "") ? "" : "?" + filter);
}

function mergeJson(jsonA, jsonB) {
	var result = {};

	if (jsonA == undefined) {
		result = jsonB;
	} else if (jsonB == undefined) {
		result = jsonA;
	} else {
		Object.keys(jsonA).forEach(key => {
			if (jsonA[key] != undefined && jsonB[key] != undefined && jsonA[key] instanceof Object && jsonB[key] instanceof Object) {
				result[key] = mergeJson(jsonA[key], jsonB[key], result);
			} else {
				result = Object.assign(jsonA, jsonB);
			}
		});
	}
	return result;
}

function fieldsToJson(container, fields) {
	var json = {};
	
	var filter = $("#" + container).find(".form-control");
	if (fields != undefined) {
		filter = $("#" + container).find(fields);
	}
		
	filter.each(function(index, value){
		var id = $(value).attr("id");
		
		if (id.indexOf(".") >= 0) {
			if ($(value).val() != "") {
				var jsonAux = {};
				var jsonNode = {};
				var array = id.split(".");
				
				for (var i = array.length - 1; i > 0; i--) {
					if (i == (array.length - 1)) {
						jsonNode[array[i]] = $(value).val();
					} else {
						jsonAux[array[i]] = jsonNode;
						jsonNode = jsonAux[array[i]];
					}
				}
	
				if ($.isEmptyObject(jsonAux)) {
					json[array[0]] = mergeJson(json[array[0]], jsonNode);
				} else {
					json[array[0]] = mergeJson(json[array[0]], jsonAux);
				}
			}
		} else {
			if (json[id] != undefined) {
				if ($(value).attr("type") == "date") {
					$(value).val(json[id].substring(0, 10));
				} else {
					$(value).val(json[id]);
				}
			}

			if ($(value).val() != "") {
				json[$(value).attr("id")] = $(value).val();
			}
		}
	});
	
	return json;
}

function fieldsRequired(container) {
	var result = true;
	
	$("#" + container).find("input[required=true], select[required=true]").each(function(index, value){
		if ($(value).attr("type") == "number") {
			if ($(value).val() == "" || $(value).val() == 0) {
				result = false;
			}
		} else {
			if ($(value).val() == "") {
				result = false;
			}
		}
	});
	
	return result;
}

function fieldsToFilter(fields) {
	return jsonToFilter(fieldsToJson(fields));
}

function setMasks() {
	$(".withMask").each(function(index, value) {
		if ($(value).attr("mask") != undefined) {
			$(value).mask($(value).attr("mask"), {reverse: true});
		}
	})
}

function loadDefaultTableActions(iconArray, registroLabel) {
	var td = $("<td/>").addClass("text-right").addClass("align-middle");
	$(iconArray).each(function (index, item){
		if (item == Dictionary.Icon.CRIAR) {
			td.append(loadDefaultIconCriar(registroLabel));
		} else if (item == Dictionary.Icon.CONSULTAR) {
			td.append(loadDefaultIconConsultar(registroLabel));
		} else if (item == Dictionary.Icon.ALTERAR) {
			td.append(loadDefaultIconAlterar(registroLabel));
		} else if (item == Dictionary.Icon.DELETAR) {
			td.append(loadDefaultIconDeletar(registroLabel));
		} else if (item == Dictionary.Icon.SEND) {
			td.append(loadDefaultIconSend(registroLabel));
		} else if (item == Dictionary.Icon.DETALHE) {
			td.append(loadDefaultIconDetalhe(registroLabel));
		}
		
		if(index < (iconArray.length-1)) {
			td.append(Dictionary.ESPACO);
		}
	});

	return td;
}

//Carregar icones
function loadDefaultIconCriar(registroLabel) {
	return loadDefaultIcon(Dictionary.Icon.CRIAR, "Criar " + registroLabel, "imgCriar" + registroLabel);
}

function loadDefaultIconConsultar(registroLabel) {
	return loadDefaultIcon(Dictionary.Icon.CONSULTAR, "Consultar " + registroLabel, "imgConsultar" + registroLabel);
}

function loadDefaultIconAlterar(registroLabel) {
	return loadDefaultIcon(Dictionary.Icon.ALTERAR, "Alterar " + registroLabel, "imgAlterar" + registroLabel);
}

function loadDefaultIconDeletar(registroLabel) {
	return loadDefaultIcon(Dictionary.Icon.DELETAR, "Deletar " + registroLabel, "imgDeletar" + registroLabel);
}

function loadDefaultIconSend(registroLabel) {
	return loadDefaultIcon(Dictionary.Icon.SEND, "Concluir " + registroLabel, "imgConcluir" + registroLabel);
}

function loadDefaultIconDetalhe(registroLabel) {
	return loadDefaultIcon(Dictionary.Icon.DETALHE, "Detalhe " + registroLabel, "imgDetalhe" + registroLabel);
}

function loadDefaultIcon(imgSrc, imgTitle, imgclass) {
	return $("<img/>").attr({
		"src": imgSrc,
		"height": "40",
		"width": "40",
		"data-toggle": "tooltip",
		"data-placement": "right",
		"title": imgTitle,
		"class": imgclass
	}).css("cursor", "pointer").tooltip();
}

//Formatar
function formatDate(date) {
	var year = date.substring(0, 4);
	var month = date.substring(5, 7);
	var day = date.substring(8, 10);

	return day + "/" + month + "/" + year;
}

function formatReal(valor) {
	return ("R$ " + valor.toFixed(2)).replace(".", ",");
}