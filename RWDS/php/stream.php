<?php

$last = exec('cd ../darkflow/darkflow/; python flow --model cfg/yolo.cfg --load bin/yolov2.weights --demo camera', $fullList, $status);

if ($status !== 0)
{
    die('command didn\'t finish as expected: '.$status);
}

echo $fullList;

exit;

?>