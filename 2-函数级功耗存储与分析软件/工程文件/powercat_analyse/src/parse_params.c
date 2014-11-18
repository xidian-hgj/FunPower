#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <stdbool.h>
#include "parse_params.h"
#include "log.h"

char filePath[PATH_MAX];


/**
 * -f file_path
 */
void parse_params(int argc, char *argv[])
{
	int ch;
	while ((ch = getopt(argc, argv, "f:d")) != -1)
	{
		switch (ch)
		{
		case 'f':
			strcpy(filePath, optarg);
			break;
		}
	}

	log("======[ init params ]=========");
	log("filePath=%s", filePath);

}
