package com.softdev.system.controller;

import com.softdev.system.service.SysConfigService;
import com.softdev.system.vo.SysConfigVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SysConfigControllerTest {

    @Mock
    private SysConfigService sysConfigService;

    @InjectMocks
    private SysConfigController sysConfigController;

    @Test
    void getById() {
        SysConfigVO vo = new SysConfigVO();
        vo.setId(1L);
        vo.setParamKey("developer");
        vo.setParamValue("test");
        when(sysConfigService.getById(1L)).thenReturn(vo);

        var result = sysConfigController.getById(1L);

        assertNotNull(result);
        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
        assertEquals("developer", result.getData().getParamKey());
    }

    @Test
    void list() {
        SysConfigVO vo1 = new SysConfigVO();
        vo1.setId(1L);
        vo1.setParamKey("k1");
        when(sysConfigService.list()).thenReturn(Arrays.asList(vo1));

        var result = sysConfigController.list();

        assertNotNull(result);
        assertEquals(200, result.getCode());
        assertEquals(1, result.getData().size());
    }

    @Test
    void getByKey() {
        SysConfigVO vo = new SysConfigVO();
        vo.setId(1L);
        vo.setParamKey("developer");
        when(sysConfigService.getByParamKey("developer")).thenReturn(vo);

        var result = sysConfigController.getByKey("developer");

        assertNotNull(result);
        assertEquals(200, result.getCode());
    }

    @Test
    void delete() {
        var result = sysConfigController.delete(1L);

        assertEquals(200, result.getCode());
        verify(sysConfigService).deleteById(1L);
    }
}
