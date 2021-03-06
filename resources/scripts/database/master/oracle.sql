CREATE SEQUENCE seq_ebms_message_id
	START WITH 1
	INCREMENT BY 1
	NOMAXVALUE;

CREATE TABLE cpa
(
	cpa_id						VARCHAR(128)		NOT NULL UNIQUE,
	cpa								CLOB						NOT NULL
);

CREATE TABLE ebms_message
(
	id								NUMBER					PRIMARY KEY,
--	parent_id					NUMBER					NULL REFERENCES ebms_message(id),
	time_stamp				TIMESTAMP				NOT NULL,
	cpa_id						VARCHAR(256)		NOT NULL,
	conversation_id		VARCHAR(256)		NOT NULL,
	sequence_nr				NUMBER					NULL,
	message_id				VARCHAR(256)		NOT NULL,
	ref_to_message_id	VARCHAR(256)		NULL,
	from_role					VARCHAR(256)		NULL,
	to_role						VARCHAR(256)		NULL,
	service_type			VARCHAR(256)		NULL,
	service						VARCHAR(256)		NOT NULL,
	action						VARCHAR(256)		NOT NULL,
	original					BLOB						NULL,
	signature					CLOB						NULL,
	message_header		CLOB						NOT NULL,
	sync_reply				CLOB						NULL,
	message_order			CLOB						NULL,
	ack_requested			CLOB						NULL,
	content						CLOB						NULL,
	status						NUMBER					NULL,
	status_time				TIMESTAMP				NULL
);

ALTER TABLE ebms_message ADD CONSTRAINT uc_ebms_message_id UNIQUE (message_id);

CREATE TABLE ebms_attachment
(
	ebms_message_id		NUMBER					NOT NULL REFERENCES ebms_message(id),
	name							VARCHAR(256)		NOT NULL,
	content_type			VARCHAR(255)		NOT NULL,
	content						BLOB						NOT NULL
);

CREATE TABLE ebms_send_event
(
	ebms_message_id		NUMBER					NOT NULL REFERENCES ebms_message(id),
	time							TIMESTAMP				DEFAULT SYSDATE NOT NULL,
	status						NUMBER					DEFAULT 0 NOT NULL,
	status_time				TIMESTAMP				DEFAULT SYSDATE NOT NULL,
--	http_status_code	NUMBER				NULL,
	UNIQUE (ebms_message_id,time)
);
