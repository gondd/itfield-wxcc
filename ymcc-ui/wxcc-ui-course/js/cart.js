// JavaScript Document
$(function() {

	// 商品选择框
	$("body").on('click', '.goods-checkbox', function() {
		var shop_id = $(this).data("shop-id");
		if ($(this).hasClass('select')) {
			$(this).removeClass('select');
			$(this).find(":checkbox").prop("checked", false);
			$('#cart_shop_' + shop_id).find(".shop-checkbox").removeClass('select');// 店铺全选取消
			$('.all-checkbox').removeClass('select');// 全选取消
		} else {
			$(this).addClass('select');
			console.log($(this).find(":checkbox"));
			$(this).find(":checkbox").prop("checked", true);

			if ($("#cart_shop_" + shop_id).find(":checkbox").size() == $("#cart_shop_" + shop_id).find(":checkbox:checked").size()) {
				$("#cart_shop_" + shop_id).find(".shop-checkbox").addClass('select');// 店铺全选选中
			}

			if ($("#cart_list").find(":checkbox").size() == $("#cart_list").find(":checkbox:checked").size()) {
				$('.all-checkbox').addClass('select');// 全选选中
			}
		}
	})

	// 店铺选择框
	$("body").on('click', '.shop-checkbox', function() {
		var shop_id = $(this).data("shop-id");
		if ($(this).hasClass('select')) {
			$('#cart_shop_' + shop_id).find('.cart-checkbox').removeClass('select');
			$('#cart_shop_' + shop_id).find(":checkbox").prop("checked", false);
			$('.all-checkbox').removeClass('select');// 全选取消
		} else {
			$('#cart_shop_' + shop_id).find('.cart-checkbox').addClass('select');
			$('#cart_shop_' + shop_id).find(":checkbox").prop("checked", true);
			if ($("#cart_list").find(":checkbox").length == $("#cart_list").find(":checkbox:checked").length) {
				$('.all-checkbox').addClass('select');// 全选选中
			}
		}
		//select();
	})

	// 全选框
	$("body").on('click', '.all-checkbox', function() {
		if ($(this).hasClass('select')) {
			$('.cart-checkbox').removeClass('select');
			$("#cart_list").find(":checkbox").prop("checked", false);
		} else {
			$('.cart-checkbox').addClass('select');
			$("#cart_list").find(":checkbox").prop("checked", true);
		}
		//select();
	})

	// 删除
	/**
	$("body").on('click', '.del', function() {
		var cart_ids = $(this).attr('data-cart-id');

		if (!cart_ids) {
			cart_ids = [];
			$("#cart_list").find(":checkbox:checked").each(function() {
				cart_ids.push($(this).val());
			})
		} else {
			cart_ids = [cart_ids];
		}

		if (cart_ids.length == 0) {
			$.msg("请选择您要移除的商品");
			return;
		}

		$.confirm("您确定要从购物车中移除选中的商品吗?", function() {
			$.cart.del(cart_ids, function(result) {
				if (result.code == 0) {
					$(".content").replaceWith(result.data);
					// 重新初始化
					init();
				}
			});
		})
	})
	**/
	// 优惠券弹框
	$("body").on('click', '.shop-coupon-trigger', function() {
		$(this).parents('.order-body').siblings().find('.SHOP-BONUS .arrow').removeClass('curr');
		$(this).parents('.order-body').siblings().find('.SHOP-BONUS .coupon-popup').hide();
		$(this).find('.arrow').toggleClass('curr');
		$(this).siblings('.coupon-popup').toggle();
	})

	// 优惠券弹框关闭
	$("body").on('click', '.coupon-popup .close', function() {
		$(this).parent('.coupon-popup').siblings('.shop-coupon-trigger').find('.arrow').removeClass('curr');
		$(this).parent('.coupon-popup').hide();
	})
		
	$(document).on("click",function(e){ 
		var target = $(e.target); 
		if(target.closest(".SHOP-BONUS").length == 0){ 
			$('.SHOP-BONUS-POPUP').hide();
			$('.SHOP-BONUS .arrow').removeClass('curr');
		} 
	}) 

	// 结算
	$("body").on('click', '.submit-btn', function() {
		submit();
	})

	// 选择商品事件
	function select() {
		var cart_ids = new Array();
		var shop_id = new Array();
		$("#cart_list").find("input[type='checkbox']:checked").each(function() {
			cart_ids.push($(this).val());
			shop_id.push($(this).attr('data-shopid'));
		})

		$.cart.select(cart_ids, function(result) {
			if (result.code == 0) {
				var data = result.data;
				$(".SZY-CART-SELECT-GOODS-NUMBER").html(data.select_goods_number);
				$(".SZY-CART-SELECT-GOODS-AMOUNT").html(data.select_goods_amount_format);
			}
		});
	}

	// 商品数量变动
	function changeNumber() {
		$(".amount").find(":text").amount({
			change: function(element, value) {

				var sku_id = $(element).data('sku-id');
				var goods_number = $(element).data('goods-number');
				var number = value;
				var max = this.max;

				$.cart.changeNumber(sku_id, number, function(result) {
					if (result.code == 0) {
						$(".content").replaceWith(result.data);
						// 重新初始化
						init();
						$(element).focus();
					} else {
						$(element).val(goods_number);
					}
				});
			}
		})
	}

	// 领取红包
	$("body").on("click", ".bonus-receive", function() {
		var bonus_id = $(this).data("bonus-id");
		var target = $(this);
		$.bonus.receive(bonus_id, function(result) {
			if (result.code == 0) {
				if (result.data == 0) {
					$(target).html("已领取").removeClass("color").removeClass("bonus-receive").addClass("bonus-received");
				}
				$.msg(result.message);
				return;
			} else if (result.code == 130) {
				$(target).html("已领取").removeClass("color").removeClass("bonus-receive").addClass("bonus-received");
			} else if (result.code == 131) {
				$(target).html("已抢光").removeClass("color").removeClass("bonus-receive").addClass("bonus-received");
			}
			$.msg(result.message, {
				time: 5000
			});
		});
	});

	function init() {
		// 初始加载商品数量变动步进器
		changeNumber();

		// 设置页面上checkbox状态
		if ($("#cart_list").find(":checkbox").size() == $("#cart_list").find(":checkbox:checked").size()) {
			$('.all-checkbox').addClass('select');// 全选选中
		}

		$(".order-body").each(function() {
			if ($(this).find(":checkbox").length == $(this).find(":checkbox:checked").length) {
				$(this).find('.shop-checkbox').addClass('select');// 店铺选中
			}
		})
	}

	// 初始化
	init();

});

// 结算
function submit() {
	$.post('/cart/go-checkout', {}, function(result) {
		$(".item-content").removeClass('bgcolor');
		if (result.code == 0) {
			// 正常提交
			top.location = result.data;
		} else if (result.code == 102) {
			$.msg(result.message, {
				time: 1500
			}, function() {
				$.go('/cart.html');
			});
		} else {
			$.msg(result.message, {
				time: 5000
			});
		}
	}, "json");
}
