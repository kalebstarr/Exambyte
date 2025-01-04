document.addEventListener("DOMContentLoaded", function () {
  let questionType = document.getElementById("questionTypeMultipleChoice");
  if (!questionType) {
    questionType = document.getElementById("questionTypeText");
  }

  // TODO: Add dynamic adding of options to existing options

  const questionContainer = document.getElementById("questionContainer");

  questionType.addEventListener("change", function () {
    const selectedType = questionType.value;
    let additionalContent = document.getElementById("additionalContent");

    if (selectedType === "Multiple Choice") {
      if (!additionalContent) {
        let optionCount = 1;
        additionalContent = document.createElement("div");
        additionalContent.id = "additionalContent";
        additionalContent.innerHTML = `
                <hr>
                <div id="optionContainer">
                  <div class="row mb-3">
                    <label for="option${optionCount}" class="col-sm-2 col-form-label">Option ${optionCount}</label>
                    <div class="col-sm-1">
                      <input type="checkbox" class="btn-check" id="correctOption${optionCount}" name="correctOptions[]" autocomplete="off" value="${optionCount}" />
                      <label for="correctOption${optionCount}" class="btn btn-outline-primary">Correct</label>
                    </div>
                    <div class="col-sm-9">
                      <input type="text" class="form-control" id="option${optionCount}" name="options[]" placeholder="Option ${optionCount}" required />
                    </div>
                  </div>
                </div>
                <a id="add-option" class="btn btn-success" href="#">Add option</a>
              `;
        questionContainer.appendChild(additionalContent);

        const addOption = document.getElementById("add-option");
        const optionContainer = document.getElementById("optionContainer");

        addOption.addEventListener("click", function (event) {
          event.preventDefault();
          optionCount++;

          const newOption = document.createElement("div");
          newOption.classList.add("row", "mb-3");
          newOption.innerHTML = `
                  <label for="option${optionCount}" class="col-sm-2 col-form-label">Option ${optionCount}</label>
                  <div class="col-sm-1">
                    <input type="checkbox" class="btn-check" id="correctOption${optionCount}" name="correctOptions[]" autocomplete="off" value="${optionCount}" />
                    <label for="correctOption${optionCount}" class="btn btn-outline-primary">Correct</label>
                  </div>
                  <div class="col-sm-9">
                    <input type="text" class="form-control" id="option${optionCount}" name="options[]" placeholder="Option ${optionCount}" required />
                  </div>
               `;
          optionContainer.appendChild(newOption);
        });
      }
    } else {
      if (additionalContent) {
        additionalContent.remove();
      }
    }
  });
});
