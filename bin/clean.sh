# delete j2meDash4.bin and/or j2meDash8.bin if they exist and delete classes_tmp and classes directories if they exist
cd ..
echo "Cleaning up..."
if [ -f "bin/j2meDash4.jar" ]; then
    rm bin/j2meDash4.jar
fi
if [ -f "bin/j2meDash8.jar" ]; then
    rm bin/j2meDash8.jar
fi
if [ -d "tmp/classes_tmp" ]; then
    rm -rf tmp/classes_tmp
fi
if [ -d "tmp/classes" ]; then
    rm -rf tmp/classes
fi
echo "Cleanup complete."
cd bin