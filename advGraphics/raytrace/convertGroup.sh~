#!/bin/bash

for name in raytrPort/*;
do foo="${name%.xwd}".jpg;
   foo=${foo#"raytrPort/"};
   foo=raytrPortJpg/"$foo";
   convert $name $foo ;
done
