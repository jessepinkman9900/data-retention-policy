-- liquibase formatted sql

-- changeset jessepinkman9900:0
CREATE TABLE IF NOT EXISTS public.table2 (
  id bigint NOT NULL,
  created_at timestamp with time zone NOT NULL,
  updated_at timestamp with time zone NOT NULL
);
-- rollback DROP TABLE IF EXISTS public.table2;

-- changeset jessepinkman9900:1
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (1, '2023-01-01 00:00:00+00:00', '2023-01-01 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 1;

-- changeset jessepinkman9900:2
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (2, '2023-01-02 00:00:00+00:00', '2023-01-02 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 2;

-- changeset jessepinkman9900:3
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (3, '2023-01-03 00:00:00+00:00', '2023-01-03 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 3;

-- changeset jessepinkman9900:4
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (4, '2023-01-04 00:00:00+00:00', '2023-01-04 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 4;

-- changeset jessepinkman9900:5
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (5, '2023-01-05 00:00:00+00:00', '2023-01-05 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 5;

-- changeset jessepinkman9900:6
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (6, '2023-01-06 00:00:00+00:00', '2023-01-06 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 6;

-- changeset jessepinkman9900:7
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (7, '2023-01-07 00:00:00+00:00', '2023-01-07 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 7;

-- changeset jessepinkman9900:8
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (8, '2023-01-08 00:00:00+00:00', '2023-01-08 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 8;

-- changeset jessepinkman9900:9
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (9, '2023-01-09 00:00:00+00:00', '2023-01-09 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 9;

-- changeset jessepinkman9900:10
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (10, '2023-01-10 00:00:00+00:00', '2023-01-10 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 10;

-- changeset jessepinkman9900:11
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (11, '2023-01-11 00:00:00+00:00', '2023-01-11 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 11;

-- changeset jessepinkman9900:12
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (12, '2023-01-12 00:00:00+00:00', '2023-01-12 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 12;

-- changeset jessepinkman9900:13
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (13, '2023-01-13 00:00:00+00:00', '2023-01-13 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 13;

-- changeset jessepinkman9900:14
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (14, '2023-01-14 00:00:00+00:00', '2023-01-14 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 14;

-- changeset jessepinkman9900:15
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (15, '2023-01-15 00:00:00+00:00', '2023-01-15 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 15;

-- changeset jessepinkman9900:16
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (16, '2023-01-16 00:00:00+00:00', '2023-01-16 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 16;

-- changeset jessepinkman9900:17
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (17, '2023-01-17 00:00:00+00:00', '2023-01-17 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 17;

-- changeset jessepinkman9900:18
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (18, '2023-01-18 00:00:00+00:00', '2023-01-18 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 18;

-- changeset jessepinkman9900:19
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (19, '2023-01-19 00:00:00+00:00', '2023-01-19 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 19;

-- changeset jessepinkman9900:20
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (20, '2023-01-20 00:00:00+00:00', '2023-01-20 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 20;

-- changeset jessepinkman9900:21
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (21, '2023-01-21 00:00:00+00:00', '2023-01-21 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 21;

-- changeset jessepinkman9900:22
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (22, '2023-01-22 00:00:00+00:00', '2023-01-22 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 22;

-- changeset jessepinkman9900:23
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (23, '2023-01-23 00:00:00+00:00', '2023-01-23 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 23;

-- changeset jessepinkman9900:24
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (24, '2023-01-24 00:00:00+00:00', '2023-01-24 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 24;

-- changeset jessepinkman9900:25
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (25, '2023-01-25 00:00:00+00:00', '2023-01-25 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 25;

-- changeset jessepinkman9900:26
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (26, '2023-01-26 00:00:00+00:00', '2023-01-26 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 26;

-- changeset jessepinkman9900:27
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (27, '2023-01-27 00:00:00+00:00', '2023-01-27 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 27;

-- changeset jessepinkman9900:28
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (28, '2023-01-28 00:00:00+00:00', '2023-01-28 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 28;

-- changeset jessepinkman9900:29
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (29, '2023-01-29 00:00:00+00:00', '2023-01-29 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 29;

-- changeset jessepinkman9900:30
INSERT INTO public.table2 (id, created_at, updated_at) VALUES (30, '2023-01-30 00:00:00+00:00', '2023-01-30 00:00:00+00:00');
-- rollback DELETE FROM public.table2 WHERE id = 30;
