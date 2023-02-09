const fileInput = document.querySelector("#imageFileInput");
const canvas = document.querySelector("#canvas");
const canvasCtx = canvas.getContext("2d");

const resetBtn = document.querySelector(".reset");

resetBtn.onclick = function () {
    resetSettings();
    renderImage();
};

const brightnessInput = document.querySelector("#brightness");
const saturationInput = document.querySelector("#saturation");
const blurInput = document.querySelector("#blur");
const inversionInput = document.querySelector("#inversion");

const settings = {
    brightness,
    saturation,
    blur,
    inversion,
};
let image = null;

function resetSettings() {
    settings.brightness = "100";
    settings.saturation = "100";
    settings.blur = "0";
    settings.inversion = "0";

    brightnessInput.value = settings.brightness;
    saturationInput.value = settings.saturation;
    blurInput.value = settings.blur;
    inversionInput.value = settings.inversion;
}

function updateSettings(key, value) {
    if (!image) return;
    settings[key] = value;
    renderImage();
}

function generateFilter() {
    const {brightness, saturation, blur, inversion} = settings;
    return `brightness(${brightness}%) saturate(${saturation}%) blur(${blur}px) invert(${inversion}%)`;
}

function renderImage() {
    canvas.width = image.width;
    canvas.height = image.height;

    canvasCtx.filter = generateFilter();
    canvasCtx.drawImage(image, 0, 0);
}

brightnessInput.addEventListener("change", () =>
    updateSettings("brightness", brightnessInput.value)
);
saturationInput.addEventListener("change", () => {
    updateSettings("saturation", saturationInput.value);
});
blurInput.addEventListener("change", () => {
    updateSettings("blur", blurInput.value);
});
inversionInput.addEventListener("change", () => {
    updateSettings("inversion", inversionInput.value);
});

fileInput.addEventListener("change", () => {
    image = new Image();

    image.addEventListener("load", () => {
        resetSettings();
        renderImage();
    });

    image.src = URL.createObjectURL(fileInput.files[0]);
});

function removebackground() {
    // let images = document.getElementById('urls').value.split('\n')

    // images.forEach(imageUrl => {
    const imagebc = new Image();
    imagebc.onload = ({target}) => {
        const w = Math.round(target.width);
        const h = Math.round(target.height);

        // const canvas = document.createElement("canvas");
        canvas.width = w;
        canvas.height = h;
        // const canvasCtx = canvas.getContext("2d");
        canvasCtx.drawImage(
            target,
            0,
            0,
            target.width,
            target.height,
            0,
            0,
            w,
            h
        );

        const canvasImageData = canvasCtx.getImageData(0, 0, w, h);

        for (
            let index = 0, dataLength = canvasImageData.data.length;
            index < dataLength;
            index += 4
        ) {
            const r = canvasImageData.data[index];
            const g = canvasImageData.data[index + 1];
            const b = canvasImageData.data[index + 2];
            if ([r, g, b].every((item) => item > 230))
                canvasImageData.data[index + 3] = 0;
        }

        target.width = w;
        target.height = h;
        canvasCtx.putImageData(canvasImageData, 0, 0);
        image = target;
        // document.body.append(image, canvas);
    };
    imagebc.crossOrigin = "";
    imagebc.src = URL.createObjectURL(fileInput.files[0]);
    // image = imagebc;
    // });
}

resetSettings();
