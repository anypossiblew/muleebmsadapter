CREATE TABLE cpa
(
	cpa_id						VARCHAR(128)		NOT NULL UNIQUE,
	cpa								CLOB						NOT NULL
);

CREATE TABLE ebms_message
(
	id								INT							GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY,
--	parent_id					INT							NULL FOREIGN KEY REFERENCES ebms_message(id),
	time_stamp				TIMESTAMP				NOT NULL,
	cpa_id						VARCHAR(256)		NOT NULL,
	conversation_id		VARCHAR(256)		NOT NULL,
	sequence_nr				BIGINT					NULL,
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
	status						INT							NULL,
	status_time				TIMESTAMP				NULL
);

CREATE TABLE ebms_attachment
(
	ebms_message_id		INT							NOT NULL FOREIGN KEY REFERENCES ebms_message(id),
	name							VARCHAR(128)		NOT NULL,
	content_type			VARCHAR(64)			NOT NULL,
	content						BLOB						NOT NULL
);

CREATE TABLE ebms_send_event
(
--	id								INT							GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY,
	ebms_message_id		INT							NOT NULL FOREIGN KEY REFERENCES ebms_message(id),
	time							TIMESTAMP				NOT NULL DEFAULT NOW(),
	status						INT							NOT NULL DEFAULT 0 ,
	status_time				TIMESTAMP				NOT NULL DEFAULT NOW()
--	http_status_code	INT							NULL
);