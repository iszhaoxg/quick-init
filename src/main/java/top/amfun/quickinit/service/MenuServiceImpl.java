package top.amfun.quickinit.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.amfun.quickinit.common.PageResponse;
import top.amfun.quickinit.entity.Menu;
import top.amfun.quickinit.mapper.MenuMapper;
import top.amfun.quickinit.mapper.RoleMenuMapper;
import top.amfun.quickinit.pojo.qo.MenuPageQuery;

import java.util.List;

@Service
class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getMenuList(Long userId) {
        return menuMapper.getMenuListByUserId(userId);
    }

    @Override
    public Menu createMenu(Menu menu) {
        menu.setMenuId(IdWorker.getId());
        menuMapper.insert(menu);
        return menu;
    }

    @Override
    public List<Menu> allMenuList() {
        return menuMapper.selectList(null);
    }

    @Override
    public Menu getMenuById(Long menuId) {
        return menuMapper.selectById(menuId);
    }

    @Override
    public PageResponse<Menu> pageSelect(MenuPageQuery query) {
        Page<Menu> page = new Page<>(query.getPage(), query.getPageSize());
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(query.getTitle())) {
            queryWrapper.like(Menu::getTitle, query.getTitle());
        }
        queryWrapper.orderByDesc(Menu::getMenuId);
        return PageResponse.restPage(menuMapper.selectPage(page, queryWrapper));
    }
}
