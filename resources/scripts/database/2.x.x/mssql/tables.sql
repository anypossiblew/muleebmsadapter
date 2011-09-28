CREATE TABLE cpa
(
	id							INT							IDENTITY(1,1)	PRIMARY KEY,
	cpa_id					VARCHAR(128)		NOT NULL UNIQUE,
	cpa							TEXT						NOT NULL
);

CREATE TABLE ebms_channel
(
	id								INT						IDENTITY(1,1)	PRIMARY KEY,
	channel_id				VARCHAR(128)	NOT NULL UNIQUE,
	cpa_id						VARCHAR(256)	NOT NULL,
	action_id					VARCHAR(64)		NOT NULL,
	endpoint					VARCHAR(64)		NULL
--	ref_id						INT						NULL FOREIGN KEY REFERENCES ebms_channel(id),
);

CREATE TABLE ebms_message
(
	id										INT							IDENTITY(1,1)	PRIMARY KEY,
--	parent_id							INT							NULL FOREIGN KEY REFERENCES ebms_message(id),
	time_stamp						DATETIME				NOT NULL,
	cpa_id								VARCHAR(256)		NOT NULL,
	conversation_id				VARCHAR(256)		NOT NULL,
	sequence_nr						INT							NOT NULL,
	message_id						VARCHAR(256)		NOT NULL UNIQUE,
	ref_to_message_id			VARCHAR(256)		NULL,
--	message_type					INT							NOT NULL,
--	message_direction			INT							NOT NULL,
	message_original			IMAGE						NULL,
	message_signature			TEXT						NULL,
	message_header				TEXT						NOT NULL,
	message_sync_reply		TEXT						NULL,
	message_order					TEXT						NULL,
	message_ack_req				TEXT						NULL,
	message_ack						TEXT						NULL,
	message_error					TEXT						NULL,
	message_manifest			TEXT						NULL,
--	message_soap_error		TEXT						NULL,
	status								INT							NOT NULL,
	status_date						DATETIME				NOT NULL,
	nr_retries						INT							DEFAULT 0 NOT NULL,
	next_retry_time				DATETIME				NULL
);

ALTER TABLE ebms_message ADD CONSTRAINT unique_ebms_message_conversation_id UNIQUE (conversation_id, cpa_id, sequence_nr);

CREATE TABLE ebms_attachment
(
	id								INT							IDENTITY(1,1)	PRIMARY KEY,
	ebms_message_id		INT							NOT NULL FOREIGN KEY REFERENCES ebms_message(id),
	name							VARCHAR(128)		NOT NULL,
	content_type			VARCHAR(64)			NOT NULL,
	content						IMAGE						NOT NULL
);
