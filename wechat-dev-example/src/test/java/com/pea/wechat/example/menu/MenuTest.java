package com.pea.wechat.example.menu;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pea.wechat.example.pojo.Token;
import com.pea.wechat.example.util.MenuUtil;
import com.pea.wechat.example.util.OAuthUtil;

public class MenuTest {
	private static Logger logger = LoggerFactory.getLogger(MenuTest.class);
	private Token token = null;
	private String appId = "wx91d907fab52860b0";

	@Before
	public void prepare() {

		// 第三方用户唯一凭证
		// 第三方用户唯一凭证密钥
		String appSecret = "da84e10c4c742cc7fac7acdd20ea1ee5";
		token = new Token();

		token.setAccessToken("U8PROJqw7EItpmiID8Al575zBScckEQ94Q5scLk7gx0CNv_bIFNyWLiAVii_zeD59GcdjbL_Y4ti1-Q8NELnl3rkqV8Q-Dqb-QFCNUI4eN0");
		token.setExpiresIn(7200);

		// 调用接口获取凭证
		// token = CommonUtil.getToken(appId, appSecret);
		// if (token != null) {
		// logger.info(token.getAccessToken());
		// logger.info(String.valueOf(token.getExpiresIn()));
		// }

	}

	@Test
	public void createMenu() {
		if (null != token) {
			// 创建菜单
			boolean result = MenuUtil.createMenu(creatMenuData(), token.getAccessToken());
			// 判断菜单创建结果
			if (result) {
				logger.info("菜单创建成功！");
			} else {
				logger.info("菜单创建失败！");
			}
		}

	}

	private Menu creatMenuData() {
		ClickButton btn11 = new ClickButton();
		btn11.setName("聚精会省");
		btn11.setType("click");
		btn11.setKey("jjhs");

		ClickButton btn21 = new ClickButton();
		btn21.setName("账户绑定1");
		btn21.setType("click");
		btn21.setKey("bind_user");

		ViewButton btn22 = new ViewButton();
		btn22.setName("账户绑定2");
		btn22.setType("view");
		String bindUrl = OAuthUtil.getOauth2CodeUrl(appId, "http://wechat-shop.aliapp.com/user/bind", "snsapi_base");
		btn22.setUrl(bindUrl);

		ViewButton btn23 = new ViewButton();
		btn23.setName("进入首页");
		btn23.setType("view");
		btn23.setUrl("http://wechat-shop.aliapp.com/user/list");

		ViewButton btn31 = new ViewButton();
		btn31.setName("E购币");
		btn31.setType("view");
		btn31.setUrl("http://www.duopao.com");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("最新活动");
		mainBtn1.setSub_button(new Button[] { btn11 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("自助服务");
		mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("产品");
		mainBtn3.setSub_button(new Button[] { btn31 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}

	@Test
	public void getMenu() {

		if (null != token) {
			// 创建菜单
			String result = MenuUtil.getMenu(token.getAccessToken());

			// 判断菜单创建结果
			if (StringUtils.isNotBlank(result)) {
				logger.info(result);
			} else {
				logger.info("获取失败！");
			}
		}

	}

	@Test
	public void deleteMenu() {
		if (null != token) {
			// 创建菜单
			boolean result = MenuUtil.deleteMenu(token.getAccessToken());

			// 判断菜单创建结果
			if (result) {
				logger.info("菜单删除成功！");
			} else {
				logger.info("菜单删除失败！");
			}
		}

	}

}
