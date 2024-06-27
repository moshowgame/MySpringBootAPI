/*
 Navicat Premium Data Transfer

 Source Server         : localhost_pg_5432
 Source Server Type    : PostgreSQL
 Source Server Version : 160002 (160002)
 Source Host           : localhost:5432
 Source Catalog        : test
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 160002 (160002)
 File Encoding         : 65001

 Date: 27/06/2024 13:33:57
*/


-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_config";
CREATE TABLE "public"."sys_config" (
  "id" int8 NOT NULL,
  "param_key" varchar(50) COLLATE "pg_catalog"."default",
  "param_value" varchar(2000) COLLATE "pg_catalog"."default",
  "status" int2,
  "remark" varchar(500) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_config"."param_key" IS 'key';
COMMENT ON COLUMN "public"."sys_config"."param_value" IS 'value';
COMMENT ON COLUMN "public"."sys_config"."status" IS '状态   0：隐藏   1：显示';
COMMENT ON COLUMN "public"."sys_config"."remark" IS '备注';
COMMENT ON TABLE "public"."sys_config" IS '系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO "public"."sys_config" VALUES (1, 'developer', 'zhengkai.blog.csdn.net', 1, '开发者');

-- ----------------------------
-- Indexes structure for table sys_config
-- ----------------------------
CREATE UNIQUE INDEX "param_key" ON "public"."sys_config" USING btree (
  "param_key" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_config
-- ----------------------------
ALTER TABLE "public"."sys_config" ADD CONSTRAINT "config_pkey" PRIMARY KEY ("id");
