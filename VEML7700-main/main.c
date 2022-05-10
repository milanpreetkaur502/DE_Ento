#include <stdio.h>
#include "VEML7700.h"


const char *i2c_bus = "/dev/apalis-i2c1";

int main()
{
	//i2c initialize
	if(i2c_init(i2c_bus) < 0)
	{
		return -1;
	}

	if(configure() == -1)
	{
		return -1;
	}

	// gain = 1 and integration time = 100ms multiplication factor = 0.0576

	while(1)
	{
		// read light intensity
		int ret_val = read_light_intensity();
		if(ret_val == -1)
		{
			return -1;
		}

		float Lux = (float)ret_val * 0.0576;

		//write Lux value to ram file
		ret_val = write_to_file(Lux);
		if(ret_val == -1)
		{
			return -1;
		}
		sleep(10);
	}

	
    
	// close the i2c bus
	if(i2c_close(i2c_bus)<0)
	{
		return -1;
	}
	return 0;
}
