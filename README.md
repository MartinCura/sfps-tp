# sfps-tp

A script is provided to simplify building the whole multi-module project.

Simply run
```
./prepare-docker-compose.sh
```
and be sure to put `train.csv` and `test.csv` in the `data/` subfolder.

Done that, you can run any service with
```
docker-compose up db [service]
```
where *service* can be any of [`dbloader`, `spark`, or `server`].


## Report

You can find the report for this project as an [Overleaf document over here](https://www.overleaf.com/read/hhfqjpcmzcpw).
