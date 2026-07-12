/*
 Sys User Table

 Source Server Type    : PostgreSQL
 File Encoding        : 65001
*/

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user";
CREATE TABLE "public"."sys_user" (
  "id" int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
  "username" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "nickname" varchar(50) COLLATE "pg_catalog"."default",
  "email" varchar(100) COLLATE "pg_catalog"."default",
  "status" int2 DEFAULT 1,
  "create_time" timestamp DEFAULT CURRENT_TIMESTAMP,
  "update_time" timestamp DEFAULT CURRENT_TIMESTAMP
)
;
COMMENT ON COLUMN "public"."sys_user"."username" IS '用户名';
COMMENT ON COLUMN "public"."sys_user"."password" IS '密码(BCrypt加密)';
COMMENT ON COLUMN "public"."sys_user"."nickname" IS '昵称';
COMMENT ON COLUMN "public"."sys_user"."email" IS '邮箱';
COMMENT ON COLUMN "public"."sys_user"."status" IS '状态 0：禁用 1：启用';
COMMENT ON TABLE "public"."sys_user" IS '系统用户表';

-- ----------------------------
-- Indexes
-- ----------------------------
CREATE UNIQUE INDEX "uk_username" ON "public"."sys_user" USING btree ("username");

-- ----------------------------
-- Primary Key
-- ----------------------------
ALTER TABLE "public"."sys_user" ADD CONSTRAINT "sys_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Default admin user (password: admin123)
-- ----------------------------
INSERT INTO "public"."sys_user"("username", "password", "nickname", "status")
VALUES ('admin', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36Kz1HEi6RDYMhHgLEoeTxC', '管理员', 1);
