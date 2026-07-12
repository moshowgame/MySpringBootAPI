package com.softdev.system.service.impl;

import com.softdev.system.entity.SysConfig;
import com.softdev.system.exception.BusinessException;
import com.softdev.system.mapper.SysConfigMapper;
import com.softdev.system.vo.SysConfigVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SysConfigServiceImplTest {

    @Mock
    private SysConfigMapper sysConfigMapper;

    @InjectMocks
    private SysConfigServiceImpl sysConfigService;

    @Test
    void getById_found() {
        SysConfig config = new SysConfig();
        config.setId(1L);
        config.setParamKey("developer");
        config.setParamValue("test");
        when(sysConfigMapper.selectById(1L)).thenReturn(config);

        SysConfigVO vo = sysConfigService.getById(1L);

        assertNotNull(vo);
        assertEquals(1L, vo.getId());
        assertEquals("developer", vo.getParamKey());
        assertEquals("test", vo.getParamValue());
    }

    @Test
    void getById_notFound() {
        when(sysConfigMapper.selectById(99L)).thenReturn(null);

        SysConfigVO vo = sysConfigService.getById(99L);

        assertNull(vo);
    }

    @Test
    void list() {
        SysConfig c1 = new SysConfig();
        c1.setId(1L);
        c1.setParamKey("k1");
        SysConfig c2 = new SysConfig();
        c2.setId(2L);
        c2.setParamKey("k2");
        when(sysConfigMapper.selectList()).thenReturn(Arrays.asList(c1, c2));

        List<SysConfigVO> list = sysConfigService.list();

        assertEquals(2, list.size());
    }

    @Test
    void getByParamKey() {
        SysConfig config = new SysConfig();
        config.setId(1L);
        config.setParamKey("developer");
        when(sysConfigMapper.selectByParamKey("developer")).thenReturn(config);

        SysConfigVO vo = sysConfigService.getByParamKey("developer");

        assertNotNull(vo);
        assertEquals("developer", vo.getParamKey());
    }

    @Test
    void getValue() {
        when(sysConfigMapper.value("developer")).thenReturn("testValue");

        String value = sysConfigService.getValue("developer");

        assertEquals("testValue", value);
    }

    @Test
    void create() {
        SysConfig config = new SysConfig();
        config.setParamKey("newKey");
        when(sysConfigMapper.insert(config)).thenReturn(1);

        assertDoesNotThrow(() -> sysConfigService.create(config));
        verify(sysConfigMapper).insert(config);
    }

    @Test
    void update_notFound() {
        SysConfig config = new SysConfig();
        config.setId(99L);
        when(sysConfigMapper.updateById(config)).thenReturn(0);

        assertThrows(BusinessException.class, () -> sysConfigService.update(config));
    }

    @Test
    void deleteById_notFound() {
        when(sysConfigMapper.deleteById(99L)).thenReturn(0);

        assertThrows(BusinessException.class, () -> sysConfigService.deleteById(99L));
    }
}
